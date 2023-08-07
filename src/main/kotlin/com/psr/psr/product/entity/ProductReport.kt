package com.psr.psr.product.entity

import com.psr.psr.global.entity.BaseEntity
import com.psr.psr.global.entity.ReportCategory
import com.psr.psr.user.entity.User
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Entity
data class ProductReport(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long,

    @ManyToOne
        @JoinColumn(nullable = false, name = "product_id")
        var product: Product,

    @ManyToOne
        @JoinColumn(nullable = false, name = "user_id")
        var user: User,

    @NotNull
        @Enumerated(EnumType.STRING)
        var category: ReportCategory

): BaseEntity()
