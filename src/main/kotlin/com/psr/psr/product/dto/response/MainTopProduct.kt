package com.psr.psr.product.dto.response

import com.psr.psr.product.entity.Product

data class MainTopProduct(
    val id: Long,
    val category: String,
    val name: String,
    val description: String
) {
    companion object {
        fun toDto(product: Product): MainTopProduct {
            return MainTopProduct(
                id = product.id!!,
                category = product.category.value,
                name = product.name,
                description = product.description
            )
        }

    }
}
