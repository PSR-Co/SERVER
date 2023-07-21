package com.psr.psr.product.entity.review

import com.psr.psr.global.entity.BaseEntity
import com.psr.psr.product.entity.ReportCategory
import com.psr.psr.user.entity.User
import jakarta.persistence.*

@Entity
data class ReviewReport(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long,

        @ManyToOne
        @JoinColumn(nullable = false, name = "review_idx")
        var review: Review,

        @ManyToOne
        @JoinColumn(nullable = false, name = "user_idx")
        var user: User,

        var category: ReportCategory


        ): BaseEntity()
