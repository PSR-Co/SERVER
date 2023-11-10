package com.psr.psr.user.dto

import com.psr.psr.global.resolver.EnumValid
import com.psr.psr.user.entity.Category
import io.swagger.v3.oas.annotations.media.Schema

data class UserInterestDto (
    @EnumValid(enumClass = Category::class, message = "올바르지 않은 상품 카테고리입니다.")
    @Schema(type = "String", description = "관심 카테고리", example = "방송가능 상품소싱", required = true,
        allowableValues = ["방송가능 상품소싱", "쇼호스트 구인", "라이브커머스 대행", "라이브커머스 교육", "스마트스토어 런칭", "영상편집", "강사매칭", "SNS 마케팅", "홍보물 디자인"])
    val category: String
)