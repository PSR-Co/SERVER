package com.psr.psr.product.entity.product

import com.psr.psr.global.entity.BaseEntity
import com.psr.psr.user.entity.Category
import com.psr.psr.user.entity.User
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Entity
data class Product(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long,

        @ManyToOne
        @JoinColumn(nullable = false, name = "user_idx")
        var user: User,

        @NotNull
        @Column(length = 50)
        var name: String,

        @NotNull
        var category: Category,

        @NotNull
        var price: Int,

        @NotNull
        var description: String,

        var likeNum: Int
): BaseEntity()
