package com.psr.psr.user.dto

import com.psr.psr.user.entity.UserInterest
import io.swagger.v3.oas.annotations.media.Schema

data class UserInterestListDto (
    @Schema(type = "List", description = "관심 목록")
    val interestList: List<UserInterestDto>?
){
    companion object{
        fun toUserInterestListDto(interests: List<UserInterest>?): UserInterestListDto {
            return UserInterestListDto(
                interestList = interests?.map { i -> UserInterestDto(i.category.value) }?.toList()
            )
        }
    }
}