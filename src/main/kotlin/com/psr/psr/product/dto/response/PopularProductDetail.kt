package com.psr.psr.product.dto.response

import com.querydsl.core.annotations.QueryProjection
import io.swagger.v3.oas.annotations.media.Schema


data class PopularProductDetail @QueryProjection constructor(
    @Schema(description = "상품 id", example = "1")
    val productId: Long,
    @Schema(description = "상품 이미지", example = "url")
    val imgUrl: String?,
    @Schema(description = "상품 이름", example = "폴로 목도리")
    val name: String,
    @Schema(description = "상품 가격", example = "35000")
    val price: Int,
    @Schema(description = "상품 좋아요 수", example = "10")
    val numOfLike: Int,
    @Schema(description = "상품 좋아요 유무", example = "true")
    val isLike: Boolean,
    @Schema(description = "평균 별점", example = "4.5")
    val avgOfRating: Double,
    @Schema(description = "리뷰 수", example = "5")
    val numOfReview: Int
)
