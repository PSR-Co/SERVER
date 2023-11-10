package com.psr.psr.review.dto

import com.psr.psr.review.entity.Review
import io.swagger.v3.oas.annotations.media.Schema

data class GetProductDetailRes(
    @Schema(description = "리뷰 수", example = "5")
    val numOfReviews: Int,
    @Schema(description = "평균 별점", example = "4.5")
    val avgOfRating: Double,
    @Schema(description = "리뷰 리스트(최대 5개)")
    val reviewList: List<ReviewDetailTop>?
) {
    companion object {
        fun toDto(reviewList: List<Review>): GetProductDetailRes {
            val ratingList = reviewList.map { r -> r.rating }
            val avgOfRating =
                if (ratingList.average().isNaN()) 0.0
                else ratingList.average()
            return GetProductDetailRes(
                numOfReviews = reviewList.size,
                avgOfRating = avgOfRating,
                reviewList = reviewList.map { r -> ReviewDetailTop.toDto(r) }.toList()
            )
        }
    }
}
