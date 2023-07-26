package com.psr.psr.global.jwt.dto

import com.psr.psr.user.entity.Type

data class TokenRes(val accessToken: String, val refreshToken: String, val type: String)