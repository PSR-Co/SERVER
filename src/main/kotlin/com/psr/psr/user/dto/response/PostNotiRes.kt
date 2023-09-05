package com.psr.psr.user.dto.response

data class PostNotiRes(
    val notification: Boolean
){
    companion object {
        fun toDto(notification: Boolean) : PostNotiRes{
            return PostNotiRes(notification)
        }
    }
}
