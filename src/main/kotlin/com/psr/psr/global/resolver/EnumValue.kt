package com.psr.psr.global.resolver

class EnumValue(
    val value: String
) {
    constructor(enumType: EnumType) : this(enumType.value)
}
