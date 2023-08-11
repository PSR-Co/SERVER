package com.psr.psr.user.repository

import com.psr.psr.user.entity.Type
import com.psr.psr.user.entity.User
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository: JpaRepository<User, Long> {
    @EntityGraph(attributePaths = ["interests"])
    fun findByIdAndStatus(id: Long, status: String) : User?
    fun existsByNickname(nickname: String): Boolean
    fun existsByPhone(phone: String): Boolean
    fun existsByEmail(nickname: String): Boolean
    fun findByEmail(email:String): Optional<User>
    fun findByNameAndPhoneAndStatus(name: String, phone: String, status: String) : User?
    fun findByEmailAndPhoneAndStatus(name: String, phone: String, status: String) : User?
    fun findByTypeAndStatus(type: Type, activeStatus: String): User?
}