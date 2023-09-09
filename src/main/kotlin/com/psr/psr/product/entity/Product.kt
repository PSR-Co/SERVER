package com.psr.psr.product.entity

import com.psr.psr.global.entity.BaseEntity
import com.psr.psr.global.entityListener.ProductEntityListener
import com.psr.psr.product.dto.request.CreateproductReq
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
        var likeNum: List<ProductLike>? = ArrayList(),

        @OneToMany(mappedBy = "product")
        @Where(clause = "status = 'active'")
        var imgs: List<ProductImg> = ArrayList(),

        @OneToMany(mappedBy = "product")
        @Where(clause = "status = 'active'")
        var reviews: List<Review>? = ArrayList()

): BaseEntity() {
        fun modifyProduct(request: CreateproductReq): Product {
                this.name = request.name
                this.category = Category.getCategoryByValue(request.category)
                this.price = request.price
                this.description = request.description
                return this
        }

    companion object {
            fun toEntity(user: User, request: CreateproductReq): Product {
                    return Product(
                            user = user,
                            name = request.name,
                            category = Category.getCategoryByValue(request.category),
                            price = request.price,
                            description = request.description
                    )
            }
    }
}
