package com.psr.psr.product.dto.response

import com.psr.psr.product.entity.Product

data class GetHomePageRes(
    val mainTopProductList: List<MainTopProduct>?,
    val recentProductList: List<MainProduct>?,
    val popularProductList: List<MainProduct>?
) {
    companion object {
        fun toDto(mainTopProductList: List<Product>?, productList: List<Product>?): GetHomePageRes {
            return GetHomePageRes(
                mainTopProductList = mainTopProductList?.sortedByDescending { it.createdAt }!!.take(3).map { p -> MainTopProduct.toDto(p) }.toList(),
                recentProductList = productList?.sortedByDescending { it.createdAt }!!.take(5).map { p -> MainProduct.toDto(p) }.toList(),
                popularProductList = productList?.sortedByDescending { it.likeNum?.size }!!.take(5).map { p -> MainProduct.toDto(p) }.toList()
            )
        }
    }
}
