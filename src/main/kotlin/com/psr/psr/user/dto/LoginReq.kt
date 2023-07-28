package com.psr.psr.user.dto

import com.psr.psr.user.entity.*
import jakarta.annotation.Nullable
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern
import org.jetbrains.annotations.NotNull
import java.util.stream.Collectors


data class LoginReq (
    @field:NotBlank
    @field:Email(message = "올바르지 않은 이메일 형식입니다.")
    val email: String,
    @field:NotBlank
    @field:Pattern(
        regexp = "^.*(?=^.{8,15}\$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#\$%^&+=]).*\$",
        message = "비밀번호를 숫자, 문자, 특수문자 포함 8~15자리 이내로 입력해주세요"
    )
    var password: String
    ) {
}