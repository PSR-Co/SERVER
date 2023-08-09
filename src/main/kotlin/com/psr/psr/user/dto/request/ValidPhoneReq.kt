package com.psr.psr.user.dto.request

import jakarta.validation.constraints.Pattern


data class ValidPhoneReq (
    @field:Pattern(
        regexp = "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})\$",
        message = "올바르지 않은 휴대폰 형식입니다."
    )
    val phone: String
)