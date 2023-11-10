package com.psr.psr.product.dto.response

import com.psr.psr.product.entity.Product
import io.swagger.v3.oas.annotations.media.Schema

data class GetHomePageRes(
    @Schema(description = "관심있는 패키지")
    val mainTopProductList: List<MainTopProduct>?,
    @Schema(description = "최신글 둘러보기")
    val recentProductList: List<MainProduct>?,
    @Schema(description = "인기 게시글")
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
