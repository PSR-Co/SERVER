package com.psr.psr.user.dto.response

import com.psr.psr.user.entity.User


data class MyPageInfoRes(
    val email: String,
    val imgUrl: String ?= null,
    val type: String,
    val phone: String,
    val nickname: String,
){
    companion object{
        fun toMyPageInfoRes(user: User) : MyPageInfoRes {
            return MyPageInfoRes(user.email, user.imgUrl, user.type.value, user.phone, user.nickname)
        }
    }
}