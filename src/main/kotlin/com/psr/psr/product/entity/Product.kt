package com.psr.psr.product.entity

import com.psr.psr.global.entity.BaseEntity
import com.psr.psr.global.entityListener.ProductEntityListener
import com.psr.psr.review.entity.Review
import com.psr.psr.user.entity.Category
import com.psr.psr.user.entity.User
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import org.jetbrains.annotations.NotNull

@Entity
@EntityListeners(ProductEntityListener::class)
@SQLDelete(sql = "UPDATE product SET status = 'inactive', updated_at = CURRENT_TIMESTAMP WHERE id = ?")
data class Product(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

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

        @OneToMany(mappedBy = "product")
        @Where(clause = "status = 'active'")
        var likeNum: List<ProductLike>? = null,

        @OneToMany(mappedBy = "product")
        @Where(clause = "status = 'active'")
        var imgs: List<ProductImg>? = null,

        @OneToMany(mappedBy = "product")
        @Where(clause = "status = 'active'")
        var reviews: List<Review>? = null

): BaseEntity()
