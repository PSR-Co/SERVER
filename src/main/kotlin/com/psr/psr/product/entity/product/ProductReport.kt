package com.psr.psr.product.entity.product

import com.psr.psr.global.entity.BaseEntity
import com.psr.psr.product.entity.ReportCategory
import com.psr.psr.user.entity.User
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Entity
data class ProductReport(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long,

        @ManyToOne
        @JoinColumn(nullable = false, name = "product_idx")
        var product: Product,

        @ManyToOne
        @JoinColumn(nullable = false, name = "user_idx")
        var user: User,

        @NotNull
        var category: ReportCategory

): BaseEntity()
