package com.psr.psr.review.dto

import com.psr.psr.review.entity.Review

data class GetProductDetailRes(
    val numOfReviews: Int,
    val avgOfRating: Double,
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
