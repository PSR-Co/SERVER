package com.psr.psr.global.jwt

import com.psr.psr.user.entity.User
import com.psr.psr.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserDetailsServiceImpl(private val userRepository: UserRepository) :UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        val user:User = userRepository.findByIdOrNull(username?.toLong() ?: 0L) ?: throw UsernameNotFoundException("사용자 id를 찾을 수 없습니다.")
        return UserAccount(user)
    }

    @Transactional
    fun loadUserById(id: Long): UserDetails {
        val user = userRepository.findById(id).orElseThrow { UsernameNotFoundException("User not found with id : $id") }
        return UserAccount(user)
    }
}