package com.psr.psr.product.dto.response

import com.querydsl.core.annotations.QueryProjection


data class PopularProductDetail @QueryProjection constructor(
    val productId: Long,
    val imgUrl: String?,
    val name: String,
    val price: Int,
    val numOfLike: Int,
    val isLike: Boolean,
    val avgOfRating: Double,
    val numOfReview: Int
)
