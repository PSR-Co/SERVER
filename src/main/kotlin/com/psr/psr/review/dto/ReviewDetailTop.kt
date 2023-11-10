package com.psr.psr.review.dto

import com.psr.psr.review.entity.Review
import io.swagger.v3.oas.annotations.media.Schema

data class ReviewDetailTop(
    @Schema(description = "리뷰 이미지", example = "url")
    val imgUrl: String?,
    @Schema(description = "리뷰 별점", example = "4.5")
    val rating: Int,
    @Schema(description = "리뷰 내용", example = "최고야- 그거야-")
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
