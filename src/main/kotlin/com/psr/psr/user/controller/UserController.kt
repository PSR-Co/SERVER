package com.psr.psr.user.controller

import com.psr.psr.user.service.UserService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(
        private val userService: UserService
) {
}