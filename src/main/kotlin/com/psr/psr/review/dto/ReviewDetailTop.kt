package com.psr.psr.review.dto

import com.psr.psr.review.entity.Review

data class ReviewDetailTop(
    val imgUrl: String?,
    val rating: Int,
    val content: String
) {
    companion object {
        fun toDto(review: Review): ReviewDetailTop {
            return ReviewDetailTop(
                imgUrl = review.imgs.firstOrNull()?.imgUrl,
                rating = review.rating,
                content = review.content
            )
        }
    }
}
