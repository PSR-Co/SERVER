package com.psr.psr.user.entity

import com.psr.psr.global.exception.BaseException
import com.psr.psr.global.exception.BaseResponseCode
import com.psr.psr.global.resolver.EnumType

enum class Category(override val value: String) : EnumType {
    BROADCAST_PRODUCT("방송가능 상품소싱"),
    SHOW_HOST_ADVERTISE("쇼호스트 구인"),
    LIVE_COMMERCE_AGENT("라이브커머스 대행"),
    LIVE_COMMERCE_EDUCATION("라이브커머스 교육"),
    SMART_STORE_LAUNCHING("스마트스토어 런칭"),
    VIDEO_EDITING("영상편집"),
    INSTRUCTOR_MATCHING("강사매칭"),
    SNS_MARKETING("SNS 마케팅"),
    PROMOTION_DESIGN("홍보물 디자인");

    companion object {
        fun getCategoryByName(name: String): Category {
            return enumValues<Category>().find { it.value == name }
                ?: throw BaseException(BaseResponseCode.INVALID_USER_CATEGORY)
        }
        fun getCategoryByValue(value: String): Category {
            return enumValues<Category>().find { it.value == value }!!
        }
    }
}