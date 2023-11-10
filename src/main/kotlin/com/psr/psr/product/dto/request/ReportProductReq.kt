package com.psr.psr.product.dto.request

import com.psr.psr.global.entity.ReportCategory
import com.psr.psr.global.resolver.EnumValid
import io.swagger.v3.oas.annotations.media.Schema

data class ReportProductReq(

    @Schema(description = "상품 카테고리", example = "방송가능 상품소싱", allowableValues = ["방송가능 상품소싱", "쇼호스트 구인", "라이브커머스 대행", "라이브커머스 교육", "스마트스토어 런칭", "영상편집", "강사매칭", "SNS 마케팅", "홍보물 디자인"])
    @EnumValid(enumClass = ReportCategory::class, message = "올바르지 않은 신고 카테고리입니다.")
    val category: String

)
