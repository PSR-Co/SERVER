package com.psr.psr.user.entity

import com.psr.psr.global.entity.BaseEntity
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Entity
data class User(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long,

        @NotNull
        @Column(length = 100)
        var email: String,

        @NotNull
        @Column(length = 300)
        var password:String,

        @NotNull
        @Enumerated(EnumType.STRING)
        var type:Type,

        @NotNull
        @Column(length = 30)
        var nickname:String,

        @NotNull
        @Column(length = 15)
        var phone:String,

        var profileImgKey: String,

        @NotNull
        @Enumerated(EnumType.STRING)
        var provider: Provider,

        @NotNull
        var marketing: Boolean,

        @NotNull
        var notification: Boolean

): BaseEntity()
