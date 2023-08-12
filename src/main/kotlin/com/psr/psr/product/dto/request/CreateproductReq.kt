package com.psr.psr.product.dto.request

import com.psr.psr.global.resolver.EnumValid
import com.psr.psr.user.entity.Category
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.PositiveOrZero
import jakarta.validation.constraints.Size

data class CreateproductReq(

    @field:NotBlank(message = "상품 카테고리을 선택해주세요.")
    @EnumValid(enumClass = Category::class, message = "올바르지 않은 상품 카테고리입니다.")
    val category: String,

    @field:NotBlank(message = "상품명을 입력해주세요.")
    val name: String,

    @field:PositiveOrZero(message = "상품 가격은 양수이어야 합니다.")
    val price: Int,

    @field:NotBlank(message = "상품 설명을 입력해주세요.")
    val description: String,

    @field:Size(min = 1, max = 5, message = "imgUrl은 null 또는 1~5개이어야 합니다.")
    val imgList: List<String>?
)
