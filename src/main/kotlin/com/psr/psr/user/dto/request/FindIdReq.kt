package com.psr.psr.user.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern


data class FindIdReq (
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
    @Schema(type = "String", description = "이름", example = "홍길동", required = true)
    val name: String
)