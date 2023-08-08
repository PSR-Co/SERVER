package com.psr.psr.user.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.psr.psr.global.Constant
import com.psr.psr.global.Constant.UserEID.UserEID.EID_URL
import com.psr.psr.global.Constant.UserEID.UserEID.PAY_STATUS
import com.psr.psr.global.Constant.UserStatus.UserStatus.ACTIVE_STATUS
import com.psr.psr.global.exception.BaseException
import com.psr.psr.global.exception.BaseResponseCode.*
import com.psr.psr.global.jwt.dto.TokenDto
import com.psr.psr.global.jwt.utils.JwtUtils
import com.psr.psr.user.dto.*
import com.psr.psr.user.dto.assembler.UserAssembler
import com.psr.psr.user.dto.eidReq.BusinessListRes
import com.psr.psr.user.entity.Category
import com.psr.psr.user.entity.Type
import com.psr.psr.user.entity.User
import com.psr.psr.user.repository.BusinessInfoRepository
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
    private val businessInfoRepository: BusinessInfoRepository,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtUtils: JwtUtils,
    private val passwordEncoder: PasswordEncoder,
    @Value("\${eid.key}")
    private val serviceKey: String,
    private val userAssembler: UserAssembler

) {
    // 회원가입
    @Transactional
    fun signUp(signUpReq: SignUpReq): TokenDto {
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
        val user = userRepository.save(userAssembler.toEntity(signUpReq))
        userInterestRepository.saveAll(userAssembler.toInterestListEntity(user, signUpReq))

        // 사업자인경우
        if (user.type == Type.ENTREPRENEUR){
            if(signUpReq.entreInfo == null) throw BaseException(NOT_EMPTY_EID)
            businessInfoRepository.save(userAssembler.toBusinessEntity(user, signUpReq))
        }

        // token 생성
        return createToken(user, password)
    }

    // 로그인
    @Transactional
    fun login(loginReq: LoginReq) : TokenDto{
        val user = userRepository.findByEmail(loginReq.email).orElseThrow{BaseException(NOT_EXIST_EMAIL)}
        if(!passwordEncoder.matches(loginReq.password, user.password)) throw BaseException(INVALID_PASSWORD)
        return createToken(user, loginReq.password)
    }

    // 닉네임 중복 체크
    fun checkDuplicateNickname(nickname: String): Boolean {
        return userRepository.existsByNickname(nickname)
    }

    // token 생성 extract method
    private fun createToken(user: User, password: String): TokenDto {
        val authenticationToken = UsernamePasswordAuthenticationToken(user.id.toString(), password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)
        return jwtUtils.createToken(authentication, user.type)
    }

    // 사용자 프로필 불러오기
    fun getProfile(user: User): ProfileRes {
        return userAssembler.toProfileRes(user)
    }

    // 사용자 프로필 변경
    @Transactional
    fun postProfile(user: User, profileReq: ProfileReq) {
        if(user.nickname != profileReq.nickname) {
            if(userRepository.existsByNickname(profileReq.nickname)) throw BaseException(EXISTS_NICKNAME)
            user.nickname = profileReq.nickname
        }
        if(user.imgUrl != profileReq.profileImgUrl) user.imgUrl = profileReq.profileImgUrl
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
        val businesses = userAssembler.toUserEidList(userEidReq)
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

    // 마이페이지 정보 불러오기
    fun getMyPageInfo(user: User): MyPageInfoRes {
        return userAssembler.toMyPageInfoRes(user)
    }

    // 토큰 재발급
    fun reissueToken(tokenDto: TokenDto): TokenDto {
        // bearer String replace blank
        userAssembler.toTokenDto(tokenDto)
        // refresh token 검증
        jwtUtils.validateToken(tokenDto.refreshToken)
        // accessToken 내 사용자 정보
        val authentication = jwtUtils.getAuthentication(tokenDto.accessToken)
        val user = userRepository.findByIdAndStatus(authentication.name.toLong(), ACTIVE_STATUS) ?: throw BaseException(NOT_FOUND_USER)
        // refreshToken이 redis 내 토큰과 같은지 확인
        jwtUtils.validateRefreshToken(user.id!!, tokenDto.refreshToken)
        // redis 내 refreshToken 삭제
        jwtUtils.deleteRefreshToken(user.id!!)
        // token 생성
        return jwtUtils.createToken(authentication, user.type)
    }

    // 비밀번호 변경
    @Transactional
    fun changePassword(user: User, passwordReq: ChangePasswordReq) {
        // 현재 비밀번호 일치 여부
        if(!passwordEncoder.matches(passwordReq.currentPassword, user.password)) throw BaseException(INVALID_PASSWORD)
        // 현재 비밀번호와 변경하려는 비밀번호 일치 여부
        if(passwordReq.currentPassword == passwordReq.password) throw BaseException(DUPLICATE_PASSWORD)

        // 비밀번호 변경
        user.password = passwordEncoder.encode(passwordReq.password)
        userRepository.save(user)
    }

    // 비밀번호 재설정
    @Transactional
    fun resetPassword(passwordReq: ResetPasswordReq) {
        val user = userRepository.findByEmail(passwordReq.email).orElseThrow{BaseException(NOT_EXIST_EMAIL)}
        if(user.phone != passwordReq.phone) throw BaseException(INVALID_PHONE)
        if(passwordEncoder.matches(passwordReq.password, user.password)) throw BaseException(DUPLICATE_PASSWORD)

        // 비밀번호 변경
        user.password = passwordEncoder.encode(passwordReq.password)
        userRepository.save(user)
    }

    // 관심 목록 변경
    @Transactional
    fun patchWatchLists(user: User,  userInterestListReq: UserInterestListReq) {
        val reqLists = userInterestListReq.interestList.map { i -> Category.getCategoryByName(i.category) }
        if(reqLists.isEmpty() || reqLists.size > 3) throw BaseException(INVALID_USER_INTEREST_COUNT)
        val userWatchLists = userInterestRepository.findByUserAndStatus(user, ACTIVE_STATUS)
        val categoryLists = userWatchLists.map { c -> c.category }

        // 사용자 관심 목록에 최근 추가한 리스트(REQUEST) 관심 목록이 없다면? => 저장
        reqLists.stream()
            .forEach { interest ->
                if(!categoryLists.contains(interest)) {
                    // 과거에 삭제했던 경우 / 새롭게 생성하는 경우
                    val interestE = userInterestRepository.findByUserAndCategory(user, interest)?: userAssembler.toInterestEntity(user, interest)
                    interestE.status = ACTIVE_STATUS
                    userInterestRepository.save(interestE)
                }
            }

        // 최근 추가한 리스트(REQUEST)에 사용자 관심 목록이 없다면? => 삭제
        userWatchLists.stream()
            .forEach { interest ->
                // todo: cascade 적용 후 모두 삭제 되었는지 확인 필요
                if(!reqLists.contains(interest.category)) userInterestRepository.delete(interest)
            }
    }
}