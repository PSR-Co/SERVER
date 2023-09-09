package com.psr.psr.product.dto.response

import com.psr.psr.product.entity.Product

data class MainProduct(
    val id: Long,
    val imgUrl: String?,
    val name: String
) {
    companion object {
        fun toDto(product: Product): MainProduct {
            return MainProduct(
                id = product.id!!,
                imgUrl = product.imgs.firstOrNull()?.imgUrl,
                name = product.name
            )
        }
    }
}
