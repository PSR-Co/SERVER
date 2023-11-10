package com.psr.psr.user.dto.response

import com.psr.psr.user.entity.User
import io.swagger.v3.oas.annotations.media.Schema


data class EmailRes(
    @Schema(type = "String", description = "이메일", example = "asdf@email.com", required = true)
    val email: String,
){
    companion object{
        fun toEmailResDto(user: User): EmailRes {
            return EmailRes(email = user.email)
        }
    }
}