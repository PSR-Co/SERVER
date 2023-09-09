package com.psr.psr.global.jwt.dto

import com.psr.psr.global.Constant
import org.jetbrains.annotations.NotNull

data class TokenDto(
    @field:NotNull
    var accessToken: String,
    @field:NotNull
    var refreshToken: String,
    val type: String ?= null
){
    companion object{
        fun toTokenDto(tokenDto: TokenDto) {
            tokenDto.accessToken.replace(Constant.JWT.BEARER_PREFIX, "").also { tokenDto.accessToken = it }
            tokenDto.refreshToken.replace(Constant.JWT.BEARER_PREFIX, "").also { tokenDto.refreshToken = it }
        }
    }
}
