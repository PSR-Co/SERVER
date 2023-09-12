package com.psr.psr.user.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.psr.psr.global.Constant
import com.psr.psr.global.Constant.UserEID.UserEID.EID_URL
import com.psr.psr.global.Constant.UserEID.UserEID.PAY_STATUS
import com.psr.psr.global.Constant.UserPhone.UserPhone.ACCESS_KEY_HEADER
import com.psr.psr.global.Constant.UserPhone.UserPhone.FINAL_URL
import com.psr.psr.global.Constant.UserPhone.UserPhone.FIRST_URL
import com.psr.psr.global.Constant.UserPhone.UserPhone.METHOD
import com.psr.psr.global.Constant.UserPhone.UserPhone.MIDDLE_URL
import com.psr.psr.global.Constant.UserPhone.UserPhone.NEWLINE
import com.psr.psr.global.Constant.UserPhone.UserPhone.SETTING_ALGORITHM
import com.psr.psr.global.Constant.UserPhone.UserPhone.SIGNATURE_HEADER
import com.psr.psr.global.Constant.UserPhone.UserPhone.SPACE
import com.psr.psr.global.Constant.UserPhone.UserPhone.TIMESTAMP_HEADER
import com.psr.psr.global.Constant.UserPhone.UserPhone.UTF_8
import com.psr.psr.global.Constant.UserStatus.UserStatus.ACTIVE_STATUS
import com.psr.psr.global.exception.BaseException
import com.psr.psr.global.exception.BaseResponseCode.*
import com.psr.psr.global.jwt.dto.TokenDto
import com.psr.psr.global.jwt.utils.JwtUtils
import com.psr.psr.user.dto.*
import com.psr.psr.user.dto.eidReq.BusinessListReq
import com.psr.psr.user.dto.eidReq.BusinessListRes
import com.psr.psr.user.dto.phoneReq.SMSReq
import com.psr.psr.user.dto.request.*
import com.psr.psr.user.dto.response.EmailRes
import com.psr.psr.user.dto.response.MyPageInfoRes
import com.psr.psr.user.dto.response.PostNotiRes
import com.psr.psr.user.dto.response.ProfileRes
import com.psr.psr.user.entity.*
import com.psr.psr.user.repository.BusinessInfoRepository
import com.psr.psr.user.repository.UserInterestRepository
import com.psr.psr.user.repository.UserRepository
import com.psr.psr.user.utils.SmsUtils
import jakarta.servlet.http.HttpServletRequest
import org.apache.commons.lang3.RandomStringUtils
import org.apache.tomcat.util.codec.binary.Base64
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
import javax.crypto.Mac

import javax.crypto.spec.SecretKeySpec

@Service
@Transactional(readOnly = true)
class UserService(
    private val userRepository: UserRepository,
    private val userInterestRepository: UserInterestRepository,
    private val businessInfoRepository: BusinessInfoRepository,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtUtils: JwtUtils,
    private val smsUtils: SmsUtils,
    private val passwordEncoder: PasswordEncoder,
    @Value("\${eid.key}")
    private val serviceKey: String,
    @Value("\${naver.cloud.sms.access-key}")
    private val accessKey: String,
    @Value("\${naver.cloud.sms.secret-key}")
    private val secretKey: String,
    @Value("\${naver.cloud.sms.serviceId}")
    private val serviceId: String,
    @Value("\${naver.cloud.sms.send-phone}")
    private val sendPhone: String,
) {
    // 회원가입
    @Transactional
    fun signUp(signUpReq: SignUpReq): TokenDto {
        // 카테고리 내 중복 값 확인
        val categoryCheck = signUpReq.interestList.groupingBy { it }.eachCount().any { it.value > 1 }
        if(categoryCheck) throw BaseException(INVALID_USER_INTEREST_COUNT)
        // category 의 사이즈 확인
        val listSize = signUpReq.interestList.size
        if(listSize < 1 || listSize > 3) throw BaseException(INVALID_USER_INTEREST_COUNT)

        // 이미 가지고 있는 이메일, 닉네임, 휴대폰 번호인지 확인
        if(userRepository.existsByEmailAndStatus(signUpReq.email, ACTIVE_STATUS)) throw BaseException(EXISTS_EMAIL)
        if(userRepository.existsByPhoneAndStatus(signUpReq.phone, ACTIVE_STATUS)) throw BaseException(EXISTS_PHONE)
        if(userRepository.existsByNicknameAndStatus(signUpReq.nickname, ACTIVE_STATUS)) throw BaseException(EXISTS_NICKNAME)

        // 암호화되지 않은 password 값 변수에 저장
        val password = signUpReq.password
        // password 암호화
        val encodedPassword = passwordEncoder.encode(signUpReq.password)
        signUpReq.password = encodedPassword
        // user 저장
        val user = userRepository.save(User.toEntity(signUpReq))
        userInterestRepository.saveAll(UserInterest.toInterestListEntity(user, signUpReq))
        // 사업자인경우
        if (user.type == Type.ENTREPRENEUR){
            if(signUpReq.entreInfo == null) throw BaseException(NOT_EMPTY_EID)
            businessInfoRepository.save(BusinessInfo.toBusinessEntity(user, signUpReq))
        }
        // token 생성
        return createToken(user, password)
    }

    // 로그인
    @Transactional
    fun login(loginReq: LoginReq) : TokenDto{
        // 이메일이 일치 확인
        val user = userRepository.findByEmailAndStatus(loginReq.email, ACTIVE_STATUS).orElseThrow{BaseException(NOT_EXIST_EMAIL)}
        // 비밀번호 일치 확인
        if(!passwordEncoder.matches(loginReq.password, user.password)) throw BaseException(INVALID_PASSWORD)
        // 알림을 위한 사용자 디바이스 토큰 저장
        if (loginReq.deviceToken != null) user.deviceToken = loginReq.deviceToken

        // token 생성
        return createToken(user, loginReq.password)
    }

    // 닉네임 중복 체크
    fun checkDuplicateNickname(nickname: String): Boolean {
        return userRepository.existsByNicknameAndStatus(nickname, ACTIVE_STATUS)
    }

    // token 생성 extract method
    private fun createToken(user: User, password: String): TokenDto {
        val authenticationToken = UsernamePasswordAuthenticationToken(user.id.toString(), password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)
        return jwtUtils.createToken(authentication, user.type)
    }

    // 사용자 프로필 불러오기
    fun getProfile(user: User): ProfileRes {
        return ProfileRes.toProfileRes(user)
    }

    // 사용자 프로필 변경
    @Transactional
    fun patchProfile(user: User, profileReq: ProfileReq) {
        // 닉네임이 변경이 되었으면
        if(user.nickname != profileReq.nickname) {
            if(userRepository.existsByNicknameAndStatusAndIdNot(profileReq.nickname, ACTIVE_STATUS, user.id!!)) throw BaseException(EXISTS_NICKNAME)
            user.nickname = profileReq.nickname
        }
        // 프로필 이미지가 변경이 되었으면
        if(user.imgUrl != profileReq.imgUrl) user.imgUrl = profileReq.imgUrl
        // 변경된 값 저장
        userRepository.save(user)
    }

    // 토큰 자동 토큰 만료 및 RefreshToken 삭제
    fun blackListToken(user: User, request: HttpServletRequest, loginStatus: String) {
        // header 에서 token 불러오기
        val token = getHeaderAuthorization(request)
        // 토큰 만료
        jwtUtils.expireToken(token, loginStatus);
        // refresh token 삭제
        jwtUtils.deleteRefreshToken(user.id!!)
    }

    // 회원 탈퇴
    @Transactional
    fun signOut(user: User) {
        userRepository.delete(user)
    }

    // 사업자 정보 확인 API
    fun validateEid(userEidReq: UserEidReq) {
        // 공공데이터 Open API 사용자 정보 불러오기
        val eidInfo = getEidInfo(userEidReq)
        // 검증을 할 수 없는 경우 (사업자를 찾지 못한 경우)
        if(eidInfo.valid_cnt == null) throw BaseException(NOT_FOUND_EID)
        // 정상 사업자가 아닌 경우
        if(eidInfo.data[0].status!!.b_stt_cd != PAY_STATUS) throw BaseException(INVALID_EID)
    }

    // 공공데이터포털에서 사용자 불러오기
    private fun getEidInfo(userEidReq: UserEidReq): BusinessListRes {
        val url = EID_URL + serviceKey
        // open api 정보 전달 객체 형태로 변경
        val businesses = BusinessListReq.toUserEidList(userEidReq)
        // json 형식으로 변경
        val json = ObjectMapper().writeValueAsString(businesses)
        // -> 이미 인코딩이 되어 있는 serviceKey이 재인코딩이 되지 않기 위해
        val factory = DefaultUriBuilderFactory(url)
        factory.encodingMode = DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY

        // open api 접근
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
        return MyPageInfoRes.toMyPageInfoRes(user)
    }

    // 토큰 재발급
    fun reissueToken(tokenDto: TokenDto): TokenDto {
        // bearer String replace blank
        TokenDto.toTokenDto(tokenDto)
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
        // 사용자 이메일 있는 경우
        val user = userRepository.findByEmailAndStatus(passwordReq.email, ACTIVE_STATUS).orElseThrow{BaseException(NOT_EXIST_EMAIL)}
        if(user.phone != passwordReq.phone) throw BaseException(INVALID_PHONE)
        if(passwordEncoder.matches(passwordReq.password, user.password)) throw BaseException(DUPLICATE_PASSWORD)

        // 비밀번호 변경
        user.password = passwordEncoder.encode(passwordReq.password)
        userRepository.save(user)
    }

    // 관심 목록 변경
    @Transactional
    fun patchWatchLists(user: User,  userInterestListReq: UserInterestListDto) {
        // 카테고리 내 중복 값 확인
        val reqLists = userInterestListReq.interestList!!.map { i -> Category.getCategoryByValue(i.category) }
        // category 의 사이즈 확인
        if(reqLists.isEmpty() || reqLists.size > 3) throw BaseException(INVALID_USER_INTEREST_COUNT)
        // 사용자 관심 목록 불러오기
        val userWatchLists = userInterestRepository.findByUserAndStatus(user, ACTIVE_STATUS)
        // string list로 변경
        val categoryLists = userWatchLists.map { c -> c.category }

        // 사용자 관심 목록에 최근 추가한 리스트(REQUEST) 관심 목록이 없다면? => 저장
        reqLists.stream()
            .forEach { interest ->
                if(!categoryLists.contains(interest)) {
                    // 과거에 삭제했던 경우 / 새롭게 생성하는 경우
                    val interestE = userInterestRepository.findByUserAndCategory(user, interest)?: UserInterest.toInterestEntity(user, interest)
                    interestE.status = ACTIVE_STATUS
                    userInterestRepository.save(interestE)
                }
            }

        // 최근 추가한 리스트(REQUEST)에 사용자 관심 목록이 없다면? => 삭제
        userWatchLists.stream()
            .forEach { interest ->
                if(!reqLists.contains(interest.category)) userInterestRepository.delete(interest)
            }
    }

    // 관심 목록 조회
    fun getWatchList(user: User) :UserInterestListDto {
        return UserInterestListDto.toUserInterestListDto(user.interests)
    }

    // 유효 휴대폰 검증
    @Transactional
    fun checkValidPhone(validPhoneReq: ValidPhoneReq) {
        val time = System.currentTimeMillis()
        val url = FIRST_URL + MIDDLE_URL + serviceId + FINAL_URL
        // -> 이미 인코딩이 되어 있는 serviceKey이 재인코딩이 되지 않기 위해
        val factory = DefaultUriBuilderFactory(url)
        factory.encodingMode = DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY
        // 인증번호 생성
        val smsKey = createSmsKey()
        // open api 접근
        WebClient.builder()
            .uriBuilderFactory(factory)
            .baseUrl(url)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader(TIMESTAMP_HEADER, time.toString())
            .defaultHeader(ACCESS_KEY_HEADER, accessKey) // accessKey
            .defaultHeader(SIGNATURE_HEADER, makeSignature(time))
            .build().post()
            .bodyValue(SMSReq.toSMSReqDto(validPhoneReq, smsKey, sendPhone))
            .retrieve()
            .onStatus({ it.isError }) { response ->
                throw BaseException(PHONE_ERROR)
            }
            .bodyToMono(String::class.java)
            .block()
        // 휴대폰 smsKey 만료시간
        smsUtils.createSmsKey(validPhoneReq.phone, smsKey)
    }

    // 휴대폰 인증번호 조회
    fun checkValidSmsKey(phone: String, smsKey: String) {
        // 휴대폰 인증 번호 불러오기
        val sms = smsUtils.getSmsKey(phone)
        // 인증코드가 같지 않은 경우 예외처리 발생
        if(sms != smsKey) throw BaseException(INVALID_SMS_KEY)
    }

    // 이메일 찾기를 위한 인증
    fun findEmailSearch(findIdPwReq: FindIdPwReq): EmailRes {
        // 인증번호 확인
        checkValidSmsKey(findIdPwReq.phone, findIdPwReq.smsKey)
        val user: User = userRepository.findByNameAndPhoneAndStatus(findIdPwReq.name!!, findIdPwReq.phone, ACTIVE_STATUS) ?: throw BaseException(NOT_FOUND_USER)
        // 사용자 이메일 전달
        return EmailRes.toEmailResDto(user)
    }

    // 비밀번호 변경을 위한 인증
    fun findPWSearch(findIdPwReq: FindIdPwReq) {
        // 인증번호 확인
        checkValidSmsKey(findIdPwReq.phone, findIdPwReq.smsKey)
        userRepository.findByEmailAndPhoneAndStatus(findIdPwReq.email!!, findIdPwReq.phone, ACTIVE_STATUS) ?: throw BaseException(NOT_FOUND_USER)
    }

    // 마이페이지 알림 수신 여부
    @Transactional
    fun postNotiStatus(user: User) : PostNotiRes{
        user.notification = !user.notification
        userRepository.save(user)
        return PostNotiRes.toDto(user.notification)
    }

    // signature For post sms
    private fun makeSignature(time: Long): String {
        val message = StringBuilder()
            .append(METHOD)
            .append(SPACE)
            .append(MIDDLE_URL + serviceId + FINAL_URL) // url
            .append(NEWLINE)
            .append(time.toString()) // time
            .append(NEWLINE)
            .append(accessKey)
            .toString()

        val signingKey = SecretKeySpec(secretKey.toByteArray(charset(UTF_8)), SETTING_ALGORITHM)
        val mac = Mac.getInstance(SETTING_ALGORITHM)
        mac.init(signingKey)

        val rawHmac = mac.doFinal(message.toByteArray(charset(UTF_8)))
        return Base64.encodeBase64String(rawHmac)
    }

    // 인증번호 생성
    fun createSmsKey() : String{
        return RandomStringUtils.random(5, false, true);
    }
}