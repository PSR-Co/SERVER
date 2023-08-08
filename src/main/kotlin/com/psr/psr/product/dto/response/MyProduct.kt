package com.psr.psr.product.dto.response

data class MyProduct(
    val productId: Long,
    val imgUrl: String,
    val category: String,
    val name: String,
    val price: Int
)
