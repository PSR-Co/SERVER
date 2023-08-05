package com.psr.psr.global.jwt.dto

import org.jetbrains.annotations.NotNull

data class TokenDto(
    @field:NotNull
    var accessToken: String,
    @field:NotNull
    var refreshToken: String,
    val type: String ?= null
)
