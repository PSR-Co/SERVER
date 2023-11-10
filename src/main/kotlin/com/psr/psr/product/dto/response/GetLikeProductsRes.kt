package com.psr.psr.product.dto.response

import com.psr.psr.product.entity.Product
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.domain.Page

data class GetLikeProductsRes(
    @Schema(description = "상품 리스트")
    val productList: Page<MyProduct>?
) {
    companion object {
        fun toDto(productList: Page<Product>?): GetLikeProductsRes {
            return GetLikeProductsRes(
                productList = productList?.map { pl -> MyProduct.toDto(pl) }
            )
        }
    }
}
