package com.psr.psr.product.entity.product

import com.psr.psr.global.entity.BaseEntity
import com.psr.psr.user.entity.User
import jakarta.persistence.*

@Entity
data class ProductLike(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long,

        @ManyToOne
        @JoinColumn(nullable = false, name = "product_id")
        var product: Product,

        @ManyToOne
        @JoinColumn(nullable = false, name = "user_id")
        var user: User

): BaseEntity()
