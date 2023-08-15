package com.psr.psr.review.entity

import com.psr.psr.global.entity.BaseEntity
import com.psr.psr.order.entity.Order
import com.psr.psr.product.entity.Product
import com.psr.psr.review.dto.ReviewReq
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import org.jetbrains.annotations.NotNull

@Entity
@SQLDelete(sql = "UPDATE review SET status = 'inactive', updated_at = current_timestamp WHERE id = ?")
data class Review(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(nullable = false, name = "product_id")
    var product: Product,

    @OneToOne(mappedBy = "review")
    var order: Order,

    @NotNull
    var rating: Int,

    var content: String,

    @OneToMany(mappedBy = "review")
    var imgs: List<ReviewImg> = ArrayList()

) : BaseEntity() {
    fun editReview(reviewReq: ReviewReq): Review {
        this.rating = reviewReq.rating
        this.content = reviewReq.content
        return this
    }
}
