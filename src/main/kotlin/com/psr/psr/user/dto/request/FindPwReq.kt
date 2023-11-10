package com.psr.psr.user.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern


data class FindPwReq (
    @field:Pattern(
        regexp = "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})\$",
        message = "올바르지 않은 휴대폰 형식입니다."
    )
    @NotBlank
    @Schema(type = "String", description = "휴대폰", example = "010-0000-0000", required = true)
    val phone: String,
    @NotBlank
    @Schema(type = "String", description = "인증코드", example = "12454", required = true)
    val smsKey: String,
    @NotBlank
    @field:Email(message = "올바르지 않은 이메일 형식입니다.")
    @Schema(type = "String", description = "이메일", example = "asdf@email.com", required = true)
    val email: String
)