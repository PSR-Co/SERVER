package com.psr.psr.user.controller

import com.psr.psr.global.dto.BaseResponse
import com.psr.psr.global.jwt.dto.TokenRes
import com.psr.psr.user.dto.SignUpReq
import com.psr.psr.user.service.UserService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(
        private val userService: UserService
) {
        /**
         * 회원가입
         */
        @PostMapping("/signup")
        @ResponseBody
        fun signUp (@RequestBody @Validated signUpReq:  SignUpReq) : BaseResponse<TokenRes>{
               return BaseResponse(userService.signUp(signUpReq))
        }
}