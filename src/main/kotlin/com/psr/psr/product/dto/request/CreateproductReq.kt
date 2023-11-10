package com.psr.psr.product.dto.request

import com.psr.psr.global.resolver.EnumValid
import com.psr.psr.user.entity.Category
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.PositiveOrZero
import jakarta.validation.constraints.Size

data class CreateproductReq(

    @Schema(description = "상품 카테고리", example = "방송가능 상품소싱", allowableValues = ["방송가능 상품소싱", "쇼호스트 구인", "라이브커머스 대행", "라이브커머스 교육", "스마트스토어 런칭", "영상편집", "강사매칭", "SNS 마케팅", "홍보물 디자인"])
    @field:NotBlank(message = "상품 카테고리을 선택해주세요.")
    @EnumValid(enumClass = Category::class, message = "올바르지 않은 상품 카테고리입니다.")
    val category: String,

    @Schema(description = "상품 이름", example = "폴로 목도리")
    @field:NotBlank(message = "상품명을 입력해주세요.")
    val name: String,

    @Schema(description = "상품 가격", example = "35000")
    @field:PositiveOrZero(message = "상품 가격은 양수이어야 합니다.")
    val price: Int,

    @Schema(description = "상품 설명", example = "방송 가능 상품입니다.")
    @field:NotBlank(message = "상품 설명을 입력해주세요.")
    val description: String,

    @Schema(description = "상품 이미지 리스트", example = "{'url','url'}")
    @field:Size(min = 1, max = 5, message = "imgUrl은 null 또는 1~5개이어야 합니다.")
    val imgList: List<String>?
)
