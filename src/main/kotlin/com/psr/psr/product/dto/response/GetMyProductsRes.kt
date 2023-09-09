package com.psr.psr.product.dto.response

import com.psr.psr.product.entity.Product
import org.springframework.data.domain.Page

data class GetMyProductsRes(
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
