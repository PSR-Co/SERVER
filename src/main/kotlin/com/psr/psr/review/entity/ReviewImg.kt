package com.psr.psr.review.entity

import com.psr.psr.global.entity.BaseEntity
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Entity
data class ReviewImg(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(nullable = false, name = "review_id")
    var review: Review,

    @NotNull
    var imgUrl: String

) : BaseEntity() {
    companion object {
        fun toEntity(review: Review, imgUrl: String): ReviewImg {
            return ReviewImg(
                review = review,
                imgUrl = imgUrl
            )
        }
    }
}
