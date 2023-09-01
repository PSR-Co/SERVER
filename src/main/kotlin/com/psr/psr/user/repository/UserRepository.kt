package com.psr.psr.user.repository

import com.psr.psr.user.entity.Type
import com.psr.psr.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository: JpaRepository<User, Long> {
    fun findByIdAndStatus(id: Long, status: String) : User?
    fun existsByNicknameAndStatus(nickname: String, status: String): Boolean
    fun existsByPhoneAndStatus(phone: String, status: String): Boolean
    fun existsByEmailAndStatus(nickname: String, status: String): Boolean
    fun findByEmailAndStatus(email:String, status: String): Optional<User>
    fun findByNameAndPhoneAndStatus(name: String, phone: String, status: String) : User?
    fun findByEmailAndPhoneAndStatus(name: String, phone: String, status: String) : User?
    fun findByTypeAndStatus(type: Type, activeStatus: String): User?
}