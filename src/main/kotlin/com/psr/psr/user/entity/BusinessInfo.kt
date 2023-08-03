package com.psr.psr.user.entity

import com.psr.psr.global.entity.BaseEntity
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import java.time.LocalDate

@Entity
data class BusinessInfo(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long ?= null,

        @OneToOne
        @JoinColumn(nullable = false, name = "user_id")
        var user: User,

        @NotNull
        var companyName: String,

        @NotNull
        var ownerName: String,

        @NotNull
        var number: String,

        @NotNull
        var date: LocalDate
): BaseEntity()
