package com.psr.psr.user.dto.response

import com.psr.psr.user.entity.User
import io.swagger.v3.oas.annotations.media.Schema


data class MyPageInfoRes(
    @Schema(type = "String", description = "이메일", example = "asdf@email.com", required = true)
    val email: String,
    @Schema(type = "String", description = "프로필 이미지", example = "htt://asdf.jpg")
    val imgUrl: String ?= null,
    @Schema(type = "String", description = "역할", example = "일반", allowableValues = ["일반", "사업자", "쇼호스트", "관리자"])
    val type: String,
    @Schema(type = "String", description = "휴대폰", example = "010-0000-0000")
    val phone: String,
    @Schema(type = "String", description = "닉네임", example = "길동이")
    val nickname: String,
){
    companion object{
        fun toMyPageInfoRes(user: User) : MyPageInfoRes {
            return MyPageInfoRes(user.email, user.imgUrl, user.type.value, user.phone, user.nickname)
        }
    }
}