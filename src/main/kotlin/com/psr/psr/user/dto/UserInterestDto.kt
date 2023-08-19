package com.psr.psr.user.dto

import com.psr.psr.global.resolver.EnumValid
import com.psr.psr.user.entity.Category

data class UserInterestDto (
    @EnumValid(enumClass = Category::class, message = "올바르지 않은 상품 카테고리입니다.")
    val category: String
)