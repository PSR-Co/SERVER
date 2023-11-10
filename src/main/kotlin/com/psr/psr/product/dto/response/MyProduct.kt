package com.psr.psr.product.dto.response

import com.psr.psr.product.entity.Product
import io.swagger.v3.oas.annotations.media.Schema

data class MyProduct(
    @Schema(description = "상품 id", example = "1")
    val productId: Long,
    @Schema(description = "상품 이미지", example = "url")
    val imgUrl: String?,
    @Schema(description = "상품 카테고리", example = "방송가능 상품소싱", allowableValues = ["방송가능 상품소싱", "쇼호스트 구인", "라이브커머스 대행", "라이브커머스 교육", "스마트스토어 런칭", "영상편집", "강사매칭", "SNS 마케팅", "홍보물 디자인"])
    val category: String,
    @Schema(description = "상품 이름", example = "폴로 목도리")
    val name: String,
    @Schema(description = "상품 가격", example = "35000")
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
