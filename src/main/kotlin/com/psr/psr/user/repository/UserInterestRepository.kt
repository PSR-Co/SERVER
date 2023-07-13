package com.psr.psr.user.repository

import com.psr.psr.user.entity.UserInterest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserInterestRepository: JpaRepository<UserInterest, Long> {
}