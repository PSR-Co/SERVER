package com.psr.psr.user.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.psr.psr.global.Constant
import com.psr.psr.global.Constant.USER_EID.USER_EID.EID_URL
import com.psr.psr.global.Constant.USER_EID.USER_EID.PAY_STATUS
import com.psr.psr.global.exception.BaseException
import com.psr.psr.global.exception.BaseResponseCode.*
import com.psr.psr.global.jwt.dto.TokenRes
import com.psr.psr.global.jwt.utils.JwtUtils
import com.psr.psr.user.dto.*
import com.psr.psr.user.dto.eidReq.BusinessListRes
import com.psr.psr.user.entity.User
import com.psr.psr.user.repository.UserInterestRepository
import com.psr.psr.user.repository.UserRepository
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.DefaultUriBuilderFactory
import java.util.stream.Collectors


@Service
@Transactional(readOnly = true)
class UserService(
    private val userRepository: UserRepository,
    private val userInterestRepository: UserInterestRepository,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtUtils: JwtUtils,
    private val passwordEncoder: PasswordEncoder,
    @Value("\${eid.key}")
    private val serviceKey: String

) {
    // 회원가입
    @Transactional
    fun signUp(signUpReq: SignUpReq): TokenRes {
        val categoryCheck = signUpReq.interestList.stream().map { i -> i.checkInterestCategory() }.collect(Collectors.toList()).groupingBy { it }.eachCount().any { it.value > 1 }
        if(categoryCheck) throw BaseException(INVALID_USER_INTEREST_COUNT)
        // category 의 사이즈 확인
        val listSize = signUpReq.interestList.size
        if(listSize < 1 || listSize > 3) throw BaseException(INVALID_USER_INTEREST_COUNT)

        // 이미 가지고 있는 이메일, 닉네임, 휴대폰 번호인지 확인
        if(userRepository.existsByEmail(signUpReq.email)) throw BaseException(EXISTS_EMAIL)
        if(userRepository.existsByPhone(signUpReq.phone)) throw BaseException(EXISTS_PHONE)
        if(userRepository.existsByNickname(signUpReq.nickname)) throw BaseException(EXISTS_NICKNAME)


        // 암호화되지 않은 password 값 저장
        val password = signUpReq.password
        // password 암호화
        val encodedPassword = passwordEncoder.encode(signUpReq.password)
        signUpReq.password = encodedPassword
        // user 저장
        val user = userRepository.save(signUpReq.toEntity())
        userInterestRepository.saveAll(signUpReq.toInterestEntity(user))

        // token 생성
        return createToken(user, password)
    }

    // 로그인
    fun login(loginReq: LoginReq) : TokenRes{
        val user = userRepository.findByEmail(loginReq.email).orElseThrow{BaseException(NOT_EXIST_EMAIL)}
        if(!passwordEncoder.matches(loginReq.password, user.password)) throw BaseException(INVALID_PASSWORD)
        return createToken(user, loginReq.password)
    }

    // 닉네임 중복 체크
    fun checkDuplicateNickname(nickname: String): Boolean {
        return userRepository.existsByNickname(nickname)
    }

    // token 생성 extract method
    private fun createToken(user: User, password: String): TokenRes {
        val authenticationToken = UsernamePasswordAuthenticationToken(user.id.toString(), password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)
        return jwtUtils.createToken(authentication, user.type)
    }

    // 사용자 프로필 불러오기
    fun getProfile(user: User): ProfileRes {
        return ProfileRes(user.email, user.imgKey)
    }

    // 사용자 프로필 변경
    @Transactional
    fun postProfile(user: User, profileReq: ProfileReq) {
        if(user.nickname != profileReq.nickname) {
            if(userRepository.existsByNickname(profileReq.nickname)) throw BaseException(EXISTS_NICKNAME)
            user.nickname = profileReq.nickname
        }
        if(user.imgKey != profileReq.profileImgKey) user.imgKey = profileReq.profileImgKey
        userRepository.save(user)
    }

    // 토큰 자동 토큰 만료 및 RefreshToken 삭제
    fun blackListToken(user: User, request: HttpServletRequest, loginStatus: String) {
        val token = getHeaderAuthorization(request)
        // 토큰 만료
        jwtUtils.expireToken(token, loginStatus);
        // refresh token 삭제
        jwtUtils.deleteRefreshToken(user.id!!)
    }

    // 회원 탈퇴
    fun signOut(user: User) {
        // todo: cascade 적용 후 모두 삭제 되었는지 확인 필요
        userRepository.delete(user)
    }

    // 사업자 정보 확인 API
    fun validateEid(userEidReq: UserEidReq) {
        val eidInfo = getEidInfo(userEidReq)
        if(eidInfo.valid_cnt == null) throw BaseException(NOT_FOUND_EID)
        if(eidInfo.data[0].status!!.b_stt_cd != PAY_STATUS) throw BaseException(INVALID_EID)
    }

    // 공공데이터포털에서 사용자 불러오기
    private fun getEidInfo(userEidReq: UserEidReq): BusinessListRes {
        val url = EID_URL + serviceKey
        val businesses = userEidReq.toList()
        val json = ObjectMapper().writeValueAsString(businesses)
        val factory = DefaultUriBuilderFactory(url)
        factory.encodingMode = DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY;
        return WebClient.builder()
            .uriBuilderFactory(factory)
            .baseUrl(url)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build().post()
            .bodyValue(json)
            .retrieve()
            .bodyToMono(BusinessListRes::class.java)
            .block()!!
    }

    // header에서 token 불러오기
    private fun getHeaderAuthorization(request: HttpServletRequest): String {
        return request.getHeader(Constant.JWT.AUTHORIZATION_HEADER)
    }
}