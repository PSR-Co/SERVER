package com.psr.psr.user.dto.response

import com.psr.psr.user.entity.User


data class ProfileRes(
    val nickname: String,
    val imgUrl: String? = null
){
    companion object{
        fun toProfileRes(user: User) : ProfileRes {
            return ProfileRes(user.nickname, user.imgUrl)
        }
    }
}