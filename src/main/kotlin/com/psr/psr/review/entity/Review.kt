package com.psr.psr.review.entity

import com.psr.psr.global.entity.BaseEntity
import com.psr.psr.order.entity.Order
import com.psr.psr.product.entity.Product
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Entity
data class Review(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        @ManyToOne
        @JoinColumn(nullable = false, name = "product_id")
        var product: Product,

        @OneToOne
        @JoinColumn(nullable = false, name = "order_id")
        var order: Order,

        @NotNull
        var rating: Int,

        var content: String

        ): BaseEntity()
