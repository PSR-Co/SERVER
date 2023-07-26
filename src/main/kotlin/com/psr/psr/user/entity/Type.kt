package com.psr.psr.user.entity

import com.psr.psr.global.exception.BaseException
import com.psr.psr.global.exception.BaseResponseCode


enum class Type(val value: String) {
    GENERAL("일반"),
    ENTREPRENEUR("사업자"),
    SHOW_HOST("쇼호스트"),
    MANAGER("관리자");

    companion object {
        fun getTypeByName(name: String): Type {
            return enumValues<Type>().find { it.value == name }
                ?: throw BaseException(BaseResponseCode.INVALID_USER_TYPE_NAME)
        }
    }
}