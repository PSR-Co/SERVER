package com.psr.psr.user.dto

import com.psr.psr.user.entity.UserInterest

data class UserInterestListDto (
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