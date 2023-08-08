package com.psr.psr.review.dto

data class ReviewDetailTop(
    val imgUrl: String?,
    val rating: Int,
    val content: String
) {
    constructor(rating: Int, content: String) : this(null, rating, content)
}
