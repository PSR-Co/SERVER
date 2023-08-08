package com.psr.psr.product.dto.response

import com.querydsl.core.annotations.QueryProjection

data class ProductDetail @QueryProjection constructor(
    val productId: Long,
    val imgUrl: String,
    val userId: Long,
    val nickname: String,
    val name: String,
    val price: Int,
    val isLike: Boolean
)
