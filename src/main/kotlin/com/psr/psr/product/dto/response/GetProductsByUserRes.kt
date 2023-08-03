package com.psr.psr.product.dto.response

data class GetProductsByUserRes(
    val imgKey: String?,
    val type: String,
    val nickname: String,
    val productList: List<MyProduct>?
)
