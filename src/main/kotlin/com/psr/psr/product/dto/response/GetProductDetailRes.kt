package com.psr.psr.product.dto.response

import com.psr.psr.product.entity.Product
import com.psr.psr.product.entity.ProductImg
import io.swagger.v3.oas.annotations.media.Schema

data class GetProductDetailRes(
    @Schema(description = "상품 좋아요 유무", example = "true")
    val isOwner: Boolean,
    @Schema(description = "상품 카테고리", example = "방송가능 상품소싱", allowableValues = ["방송가능 상품소싱", "쇼호스트 구인", "라이브커머스 대행", "라이브커머스 교육", "스마트스토어 런칭", "영상편집", "강사매칭", "SNS 마케팅", "홍보물 디자인"])
    val category: String,
    @Schema(description = "상품 이미지 리스트", example = "{'url','url'}")
    val imgList: List<String>,
    @Schema(description = "판매자 id", example = "2")
    val userId: Long?,
    @Schema(description = "판매자 닉네임", example = "소징")
    val nickname: String,
    @Schema(description = "상품 좋아요 수", example = "10")
    val numOfLikes: Int,
    @Schema(description = "상품 이름", example = "폴로 목도리")
    val name: String,
    @Schema(description = "상품 가격", example = "35000")
    val price: Int,
    @Schema(description = "상품 설명", example = "방송 가능 상품입니다.")
    val description: String,
    @Schema(description = "좋아요 유무", example = "true")
    val isLike: Boolean
) {
    companion object {
        fun toDto(isOwner: Boolean, product: Product, imgList: List<ProductImg>, numOfLikes: Int, isLike: Boolean): GetProductDetailRes {
            return GetProductDetailRes(
                isOwner = isOwner,
                category = product.category.value,
                imgList = imgList.map { i -> i.imgUrl }.toList(),
                userId = product.user.id,
                nickname = product.user.nickname,
                numOfLikes = numOfLikes,
                name = product.name,
                price = product.price,
                description = product.description,
                isLike = isLike
            )
        }
    }
}
