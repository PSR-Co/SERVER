package com.psr.psr.user.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern


data class SendSmsReq (
    @field:NotBlank
    @Schema(type = "String", description = "휴대폰", example = "01000000000", required = true)
    val phone: String
)