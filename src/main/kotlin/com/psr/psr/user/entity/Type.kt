package com.psr.psr.user.entity

import com.psr.psr.global.resolver.EnumType


enum class Type(override val value: String) : EnumType {
    GENERAL("일반"),
    ENTREPRENEUR("사업자"),
    SHOW_HOST("쇼호스트"),
    MANAGER("관리자");

    companion object {
        fun getTypeByValue(value: String): Type {
            return enumValues<Type>().find { it.value == value }!!
        }
    }
}