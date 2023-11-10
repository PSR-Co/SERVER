package com.psr.psr.product.dto.response

import com.psr.psr.product.entity.Product
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.domain.Page

data class GetMyProductsRes(
    @Schema(description = "상품 리스트")
    val productList: Page<MyProduct>?
) {
    companion object {
        fun toDto(productList: Page<Product>?): GetMyProductsRes {
            return GetMyProductsRes(
                productList = productList?.map { MyProduct.toDto(it) }
            )
        }
    }
}
