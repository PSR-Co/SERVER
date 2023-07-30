package com.psr.psr.user.dto

import jakarta.validation.constraints.NotBlank


data class CheckNicknameReq (
    @field:NotBlank
    val nickname: String
)