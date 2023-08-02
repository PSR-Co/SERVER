package com.psr.psr.global.jwt

import com.psr.psr.global.Constant.USER_STATUS.USER_STATUS.ACTIVE_STATUS
import com.psr.psr.global.exception.BaseException
import com.psr.psr.global.exception.BaseResponseCode
import com.psr.psr.global.exception.BaseResponseCode.NOT_FOUND_USER
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
        val user:User = userRepository.findByIdAndStatus(username?.toLong() ?: 0L, ACTIVE_STATUS) ?: throw BaseException(NOT_FOUND_USER)
        return UserAccount(user)
    }

    @Transactional
    fun loadUserById(id: Long): UserDetails {
        val user:User = userRepository.findByIdAndStatus(id, ACTIVE_STATUS) ?: throw BaseException(NOT_FOUND_USER)
        return UserAccount(user)
    }
}