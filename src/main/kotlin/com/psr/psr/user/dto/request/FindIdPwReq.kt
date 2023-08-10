package com.psr.psr.user.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern


data class FindIdPwReq (
    @field:Pattern(
        regexp = "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})\$",
        message = "올바르지 않은 휴대폰 형식입니다."
    )
    @NotNull
    val phone: String,
    val smsKey: String,
    val name: String ?= null,
    @field:Email(message = "올바르지 않은 이메일 형식입니다.")
    val email: String ?= null
)