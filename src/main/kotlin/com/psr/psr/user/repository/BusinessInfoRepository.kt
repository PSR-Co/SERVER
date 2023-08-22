package com.psr.psr.user.repository

import com.psr.psr.user.entity.BusinessInfo
import com.psr.psr.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BusinessInfoRepository: JpaRepository<BusinessInfo, Long> {
    fun deleteByUser(user: User)
}