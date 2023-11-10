package com.psr.psr.product.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.domain.Page

data class GetSearchProducts(
    @Schema(description = "상품 목록")
    val productList: Page<ProductDetail>
) {
    companion object {
        fun toDto(productList: Page<ProductDetail>): GetSearchProducts {
            return GetSearchProducts(
                productList = productList
            )
        }
    }
}
