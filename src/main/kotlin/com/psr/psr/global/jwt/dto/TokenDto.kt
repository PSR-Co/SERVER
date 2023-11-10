package com.psr.psr.global.jwt.dto

import com.psr.psr.global.Constant
import io.swagger.v3.oas.annotations.media.Schema
import org.jetbrains.annotations.NotNull

data class TokenDto(
    @field:NotNull
    @Schema(type = "String", description = "AccessToken", required = true, example = "bearer eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2OTY3NTg2OTcsInVzZXJJZHgiOjEsInN1YiI6IjEifQ.DSBuBlStkjhT05vuzjWd-cg7naG5KikUxII734u3nUw")
    var accessToken: String,
    @field:NotNull
    @Schema(type = "String", description = "AccessToken", required = true, example = "bearer eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2OTY3NTg2OTcsInVzZXJJZHgiOjEsInN1YiI6IjEifQ.DSBuBlStkjhT05vuzjWd-cg7naG5KikUxII734u3nUw")
    var refreshToken: String,
    @Schema(type = "String", description = "역할", example = "일반", allowableValues = ["일반", "사업자", "쇼호스트", "관리자"])
    val type: String ?= null
){
    companion object{
        fun toTokenDto(tokenDto: TokenDto) {
            tokenDto.accessToken.replace(Constant.JWT.BEARER_PREFIX, "").also { tokenDto.accessToken = it }
            tokenDto.refreshToken.replace(Constant.JWT.BEARER_PREFIX, "").also { tokenDto.refreshToken = it }
        }
    }
}
