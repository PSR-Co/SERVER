package com.psr.psr.user.dto

import com.psr.psr.user.entity.Provider
import com.psr.psr.user.entity.Type
import com.psr.psr.user.entity.User

data class SignUpReq (
    val email: String,
    var password: String,
    val type: String,
    val phone: String,
    val nickname: String,
    val marketing: Boolean,
    val notification: Boolean
    ) {
    fun toEntity(): User {
        return User(email = email,
            password = password,
            type = Type.getTypeByName(type),
            phone = phone,
            provider = Provider.LOCAL,
            marketing = marketing,
            notification = notification,
            nickname = nickname)
    }
}