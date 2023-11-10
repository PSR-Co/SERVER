package com.psr.psr.product.dto.response

import com.querydsl.core.annotations.QueryProjection
import io.swagger.v3.oas.annotations.media.Schema

data class ProductDetail @QueryProjection constructor(
    @Schema(description = "상품 id", example = "1")
    val productId: Long,
    @Schema(description = "상품 이미지", example = "url")
    val imgUrl: String?,
    @Schema(description = "판매자 id", example = "2")
    val userId: Long,
    @Schema(description = "판매자 닉네임", example = "소징")
    val nickname: String,
    @Schema(description = "상품 이름", example = "초코나무숲")
    val name: String,
    @Schema(description = "상품 가격", example = "35000")
    val price: Int,
    @Schema(description = "상품 좋아요 유무", example = "true")
    val isLike: Boolean
)
