package com.psr.psr.product.dto.response

import com.psr.psr.product.entity.Product
import org.springframework.data.domain.Page

data class GetLikeProductsRes(
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
