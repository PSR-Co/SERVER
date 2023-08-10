package com.psr.psr.user.dto.request

import com.psr.psr.user.entity.Category

data class UserInterestReq (
    val category: String
){
    fun checkInterestCategory() : Category{
        return Category.getCategoryByName(category)
    }
}