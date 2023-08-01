package com.psr.psr.product.dto.response

import com.querydsl.core.annotations.QueryProjection

data class ProductDetail @QueryProjection constructor(
    val productIdx: Long,
    val imgKey: String,
    val userIdx: Long,
    val nickname: String,
    val name: String,
    val price: Int,
    val isLike: Boolean
)
