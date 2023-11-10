package com.psr.psr.product.dto.response

import com.psr.psr.product.entity.Product
import io.swagger.v3.oas.annotations.media.Schema

data class MainProduct(
    @Schema(description = "상품 id", example = "1")
    val id: Long,
    @Schema(description = "상품 이미지", example = "url")
    val imgUrl: String?,
    @Schema(description = "상품 이름", example = "초코나무숲")
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
