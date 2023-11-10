package com.psr.psr.user.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class ChangePasswordReq (
    @field:NotBlank
    @field:Pattern(
        regexp = "^.*(?=^.{8,15}\$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#\$%^&+=]).*\$",
        message = "비밀번호를 숫자, 문자, 특수문자 포함 8~15자리 이내로 입력해주세요"
    )
    @Schema(type = "String", description = "기존 비밀번호", example = "asdf1234!", required = true)
    val currentPassword: String,
    @field:NotBlank
    @field:Pattern(
        regexp = "^.*(?=^.{8,15}\$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#\$%^&+=]).*\$",
        message = "비밀번호를 숫자, 문자, 특수문자 포함 8~15자리 이내로 입력해주세요"
    )
    @Schema(type = "String", description = "변경할 비밀번호", example = "asdf1234!", required = true)
    val password: String
)