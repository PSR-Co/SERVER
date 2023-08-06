package com.psr.psr.cs.entity

import com.psr.psr.global.exception.BaseException
import com.psr.psr.global.exception.BaseResponseCode

enum class FaqType(val value: String) {
    ACCOUNT_MANAGEMENT("계정관리"),
    CONSULTING("컨설팅"),
    PRODUCT("상품");

    companion object {
        fun getTypeByName(name: String): FaqType {
            return enumValues<FaqType>().find { it.value == name }
                ?: throw BaseException(BaseResponseCode.INVALID_FAQ_TYPE_NAME)
        }
    }
}