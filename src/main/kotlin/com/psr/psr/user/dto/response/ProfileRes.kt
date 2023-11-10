package com.psr.psr.user.dto.response

import com.psr.psr.user.entity.User
import io.swagger.v3.oas.annotations.media.Schema


data class ProfileRes(
    @Schema(type = "String", description = "닉네임", example = "길동이")
    val nickname: String,
    @Schema(type = "String", description = "프로필 이미지", example = "htt://asdf.jpg")
    val imgUrl: String? = null
){
    companion object{
        fun toProfileRes(user: User) : ProfileRes {
            return ProfileRes(user.nickname, user.imgUrl)
        }
    }
}