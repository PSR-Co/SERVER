package com.psr.psr.user.entity

import com.psr.psr.global.entity.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.jetbrains.annotations.NotNull

@Entity
data class User(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long,

        @NotNull
        var email: String,

        @NotNull
        var password:String,

        @NotNull
        var type:Type,

        @NotNull
        var nickname:String,

        @NotNull
        var phone:String,

        var profileImgKey: String,

        @NotNull
        var provider: Provider,

        @NotNull
        var marketing: Boolean,

        @NotNull
        var notification: Boolean

): BaseEntity()
