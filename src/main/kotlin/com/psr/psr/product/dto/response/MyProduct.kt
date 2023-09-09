package com.psr.psr.product.dto.response

import com.psr.psr.product.entity.Product

data class MyProduct(
    val productId: Long,
    val imgUrl: String?,
    val category: String,
    val name: String,
    val price: Int
) {
    companion object {
        fun toDto(product: Product): MyProduct {
            return MyProduct(
                productId = product.id!!,
                imgUrl = product.imgs.firstOrNull()?.imgUrl,
                category = product.category.value,
                name = product.name,
                price = product.price
            )
        }
    }
}
