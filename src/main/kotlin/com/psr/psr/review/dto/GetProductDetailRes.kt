package com.psr.psr.review.dto

data class GetProductDetailRes(
    val numOfReviews: Int,
    val avgOfRating: Double,
    val reviewList: List<ReviewDetailTop>?
)
