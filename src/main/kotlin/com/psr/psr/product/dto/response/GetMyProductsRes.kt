package com.psr.psr.product.dto.response

import com.psr.psr.global.Constant
import com.psr.psr.product.entity.Product
import com.psr.psr.user.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

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
