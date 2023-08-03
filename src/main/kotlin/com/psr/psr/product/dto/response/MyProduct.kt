package com.psr.psr.product.dto.response

data class MyProduct(
    val productId: Long,
    val imgKey: String,
    val category: String,
    val name: String,
    val price: Int
)
