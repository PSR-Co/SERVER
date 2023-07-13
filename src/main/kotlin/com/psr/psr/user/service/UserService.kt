package com.psr.psr.user.service

import com.psr.psr.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
        private val userRepository: UserRepository
) {
}