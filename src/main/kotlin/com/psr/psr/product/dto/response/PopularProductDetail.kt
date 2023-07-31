package com.psr.psr.product.dto.response

import com.querydsl.core.annotations.QueryProjection


data class PopularProductDetail @QueryProjection constructor(
    val productIdx: Long,
    val imgKey: String,
    val name: String,
    val price: Int,
    val numOfLike: Int,
    val isLike: Boolean,
    val avgOfRating: Double,
    val numOfReview: Int
)
