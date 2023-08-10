package com.psr.psr.global.resolver


import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class EnumValidator : ConstraintValidator<EnumValid, String> {
    private lateinit var annotation: EnumValid

    override fun initialize(constraintAnnotation: EnumValid) {
        this.annotation = constraintAnnotation
    }

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        val enumConstants = this.annotation.enumClass.java.enumConstants
        if (enumConstants.isNotEmpty()) {
            val enumValues: List<EnumValue> = enumConstants.map { EnumValue(it) }.toList()
            for (enumValue in enumValues) {
                if (value.equals(enumValue.value)) return true;
            }
        }
        return false;
    }

}