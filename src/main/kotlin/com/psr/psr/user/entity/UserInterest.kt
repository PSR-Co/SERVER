package com.psr.psr.user.entity

import com.psr.psr.global.entity.BaseEntity
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Entity
data class UserInterest(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(nullable = false, name = "user_id")
        var user: User,

        @NotNull
        @Enumerated(EnumType.STRING)
        var category: Category

): BaseEntity()
