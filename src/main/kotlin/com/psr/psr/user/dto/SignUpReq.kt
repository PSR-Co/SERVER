package com.psr.psr.user.dto

import com.psr.psr.user.entity.*
import java.util.stream.Collector
import java.util.stream.Collectors




data class SignUpReq (
    val email: String,
    var password: String,
    val type: String,
    val phone: String,
    val imgKey: String,
    val nickname: String,
    val marketing: Boolean,
    val notification: Boolean,
    val interestList: List<UserInterestReq>
    ) {
    fun toEntity(): User {
        return User(email = email,
            password = password,
            type = Type.getTypeByName(type),
            phone = phone,
            imgKey = imgKey,
            provider = Provider.LOCAL,
            marketing = marketing,
            notification = notification,
            nickname = nickname)
    }

    fun toInterestEntity(user: User): List<UserInterest> {
        return interestList.stream()
            .map { i ->
                UserInterest(category = Category.getCategoryByName(i.category),
                    user = user)
            }.collect(Collectors.toList())
    }
}