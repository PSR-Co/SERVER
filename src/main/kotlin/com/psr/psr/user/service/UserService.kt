package com.psr.psr.user.service

import com.fasterxml.jackson.databind.ser.Serializers.Base
import com.psr.psr.global.Constant.User.User.EMAIL_VALIDATION
import com.psr.psr.global.Constant.User.User.PASSWORD_VALIDATION
import com.psr.psr.global.Constant.User.User.PHONE_VALIDATION
import com.psr.psr.global.exception.BaseException
import com.psr.psr.global.exception.BaseResponseCode
import com.psr.psr.global.jwt.dto.TokenRes
import com.psr.psr.global.jwt.utils.JwtUtils
import com.psr.psr.user.dto.SignUpReq
import com.psr.psr.user.entity.Category
import com.psr.psr.user.entity.User
import com.psr.psr.user.entity.UserInterest
import com.psr.psr.user.repository.UserInterestRepository
import com.psr.psr.user.repository.UserRepository
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.regex.Pattern
import java.util.stream.Collectors

@Service
@Transactional(readOnly = true)
class UserService(
    private val userRepository: UserRepository,
    private val userInterestRepository: UserInterestRepository,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtUtils: JwtUtils,
    private val passwordEncoder: PasswordEncoder

) {
    // 회원가입
    @Transactional
    fun signUp(signUpReq: SignUpReq): TokenRes {
        // 이메일의 형식이 맞는지 확인
        if(!isValidRegularExpression(signUpReq.email, EMAIL_VALIDATION)) throw BaseException(BaseResponseCode.INVALID_EMAIL)
        // 비밀번호의 형식이 맞는지 확인
        if(!isValidRegularExpression(signUpReq.password, PASSWORD_VALIDATION)) throw BaseException(BaseResponseCode.INVALID_PASSWORD)
        // 휴대폰 번호의 형식이 맞는지 확인
        if(!isValidRegularExpression(signUpReq.phone, PHONE_VALIDATION)) throw BaseException(BaseResponseCode.INVALID_PHONE)
        // Category가 알맞은 이름을 갖고 있는지, 값이 중복되어있는지 확인
        val categoryCheck = signUpReq.interestList.stream().map { i -> i.checkInterestCategory() }.collect(Collectors.toList()).groupingBy { it }.eachCount().any { it.value > 1 }
        if(categoryCheck) throw BaseException(BaseResponseCode.INVALID_USER_INTEREST_COUNT)
        // category 의 사이즈 확인
        val listSize = signUpReq.interestList.size
        if(listSize < 1 || listSize > 3) throw BaseException(BaseResponseCode.INVALID_USER_INTEREST_COUNT)

        // 이미 가지고 있는 이메일, 닉네임, 휴대폰 번호인지 확인
        if(userRepository.existsByEmail(signUpReq.email)) throw BaseException(BaseResponseCode.EXISTS_EMAIL)
        if(userRepository.existsByPhone(signUpReq.phone)) throw BaseException(BaseResponseCode.EXISTS_PHONE)
        if(userRepository.existsByNickname(signUpReq.nickname)) throw BaseException(BaseResponseCode.EXISTS_NICKNAME)


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

    // 정규 표현식 확인 extract method
    private fun isValidRegularExpression(word: String, validation: String) : Boolean{
        val pattern = Pattern.compile(validation)
        return pattern.matcher(word).matches()
    }

    // token 생성 extract method
    private fun createToken(user: User, password: String): TokenRes {
        val authenticationToken = UsernamePasswordAuthenticationToken(user.id.toString(), password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)
        return jwtUtils.createToken(authentication, user.type)
    }
}