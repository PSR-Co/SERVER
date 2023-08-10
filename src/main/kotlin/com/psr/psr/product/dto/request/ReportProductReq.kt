package com.psr.psr.product.dto.request

import com.psr.psr.global.entity.ReportCategory
import com.psr.psr.global.resolver.EnumValid

data class ReportProductReq(

    @EnumValid(enumClass = ReportCategory::class, message = "올바르지 않은 신고 카테고리입니다.")
    val category: String

)
