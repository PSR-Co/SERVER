package com.psr.psr.user.dto.response


data class MyPageInfoRes(
    val email: String,
    val imgUrl: String ?= null,
    val type: String,
    val phone: String,
)