package com.psr.psr.product.dto.response

data class GetHomePageRes(
    val mainTopProductList: List<MainTopProduct>?,
    val recentProductList: List<MainProduct>?,
    val popularProductList: List<MainProduct>?
)
