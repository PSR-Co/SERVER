package com.psr.psr.order.entity

import com.psr.psr.global.entity.BaseEntity
import com.psr.psr.product.entity.product.Product
import com.psr.psr.user.entity.User
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Entity
data class Order(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long,

        @ManyToOne
        @JoinColumn(nullable = false, name = "product_idx")
        var product: Product,

        @ManyToOne
        @JoinColumn(nullable = false, name = "user_idx")
        var user: User,

        @NotNull
        var ordererName: String,

        @NotNull
        var websiteUrl: String,

        @NotNull
        var orderStatus: OrderStatus,

        @NotNull
        var inquiry: String,

        @NotNull
        var description: String

): BaseEntity()
