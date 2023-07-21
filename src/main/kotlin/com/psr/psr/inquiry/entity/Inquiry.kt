package com.psr.psr.inquiry.entity

import com.psr.psr.global.entity.BaseEntity
import com.psr.psr.user.entity.User
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Entity
data class Inquiry(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long,

        @ManyToOne
        @JoinColumn(nullable = false, name = "user_idx")
        var user: User,

        @NotNull
        var title: String,

        @NotNull
        var content: String,

        @NotNull
        var inquiryStatus: InquiryStatus,

        var answer: String

): BaseEntity()
