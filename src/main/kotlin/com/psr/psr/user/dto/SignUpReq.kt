package com.psr.psr.user.dto

import com.psr.psr.user.entity.*
import jakarta.annotation.Nullable
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern
import org.jetbrains.annotations.NotNull
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.stream.Collectors


data class SignUpReq (
    @field:NotBlank
    @field:Email(message = "올바르지 않은 이메일 형식입니다.")
    val email: String,
    @field:NotBlank
    @field:Pattern(
        regexp = "^.*(?=^.{8,15}\$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#\$%^&+=]).*\$",
        message = "비밀번호를 숫자, 문자, 특수문자 포함 8~15자리 이내로 입력해주세요"
    )
    var password: String,
    @field:NotBlank
    val type: String,
    @field:Pattern(
        regexp = "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})\$",
        message = "올바르지 않은 휴대폰 형식입니다."
    )
    val phone: String,
    @field:Nullable
    val imgKey: String? = null,
    @field:NotBlank
    @field:Pattern(
        regexp = "^([a-zA-Z0-9ㄱ-ㅎ|ㅏ-ㅣ|가-힣]).{0,10}\$",
        message = "한글, 영어, 숫자만 입력해주세요. (10글자)"
    )
    val nickname: String,
    val marketing: Boolean,
    @field:NotNull
    val notification: Boolean,
    @field:NotEmpty
    val interestList: List<UserInterestReq>,
    val entreInfo: UserEidReq ?= null
    )