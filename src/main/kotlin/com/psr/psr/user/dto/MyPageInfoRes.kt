package com.psr.psr.user.dto


data class MyPageInfoRes(
    val email: String,
    val imgKey: String ?= null,
    val type: String,
    val phone: String,
)