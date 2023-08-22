package com.psr.psr.user.repository

import com.psr.psr.user.entity.Category
import com.psr.psr.user.entity.User
import com.psr.psr.user.entity.UserInterest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserInterestRepository: JpaRepository<UserInterest, Long> {

    fun findByUserAndStatus(user: User, status: String): List<UserInterest>
    fun findByUserAndCategory(user: User, category: Category): UserInterest ?
    fun deleteByUser(user: User)
}