package com.psr.psr.user.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class ResetPasswordReq (
    @field:NotBlank(message = "이메일을 입력해주세요.")
    @field:Email(message = "올바르지 않은 이메일 형식입니다.")
    @Schema(type = "String", description = "이메일", example = "asdf@email.com", required = true)
    val email: String,
    @field:NotBlank(message = "휴대폰을 입력해주세요.")
    @field:Pattern(
        regexp = "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})\$",
        message = "올바르지 않은 휴대폰 형식입니다."
    )
    @Schema(type = "String", description = "휴대폰", example = "010-0000-0000", required = true)
    val phone: String,
    @field:NotBlank(message = "비밀번호를 입력해주세요.")
    @field:Pattern(
        regexp = "^.*(?=^.{8,15}\$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#\$%^&+=]).*\$",
        message = "비밀번호를 숫자, 문자, 특수문자 포함 8~15자리 이내로 입력해주세요"
    )
    @Schema(type = "String", description = "비밀번호", example = "asdf1234!", required = true)
    val password: String
)