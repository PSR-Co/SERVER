package com.psr.psr.product.dto.response

import com.psr.psr.product.entity.Product
import com.psr.psr.user.entity.User
import org.springframework.data.domain.Page

data class GetProductsByUserRes(
    val imgUrl: String?,
    val type: String,
    val nickname: String,
    val productList: Page<MyProduct>?
) {
    companion object {
        fun toDto(user: User, productList: Page<Product>?): GetProductsByUserRes {
            return GetProductsByUserRes(
                imgUrl = user.imgUrl,
                type = user.type.value,
                nickname = user.nickname,
                productList = productList?.map { p -> MyProduct.toDto(p) }
            )
        }
    }
}
