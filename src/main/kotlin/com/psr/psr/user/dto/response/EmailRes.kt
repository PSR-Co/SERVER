package com.psr.psr.user.dto.response

import com.psr.psr.user.entity.User


data class EmailRes(
    val email: String,
){
    companion object{
        fun toEmailResDto(user: User): EmailRes {
            return EmailRes(email = user.email)
        }
    }
}