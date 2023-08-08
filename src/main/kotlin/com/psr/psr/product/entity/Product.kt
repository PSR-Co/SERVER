package com.psr.psr.product.entity

import com.psr.psr.global.entity.BaseEntity
import com.psr.psr.user.entity.Category
import com.psr.psr.user.entity.User
import jakarta.persistence.*
import org.hibernate.annotations.Where
import org.jetbrains.annotations.NotNull

@Entity
data class Product(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long,

        @ManyToOne
        @JoinColumn(nullable = false, name = "user_id")
        var user: User,

        @NotNull
        @Column(length = 50)
        var name: String,

        @NotNull
        @Enumerated(EnumType.STRING)
        var category: Category,

        @NotNull
        var price: Int,

        @NotNull
        var description: String,

        var likeNum: Int = 0,

        @OneToMany(mappedBy = "product")
        @Where(clause = "status = 'active'")
        var imgs: List<ProductImg>

): BaseEntity()
