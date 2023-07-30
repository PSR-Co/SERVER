package com.psr.psr.user.controller

import com.psr.psr.global.dto.BaseResponse
import com.psr.psr.global.jwt.dto.TokenRes
import com.psr.psr.user.dto.LoginReq
import com.psr.psr.user.dto.SignUpReq
import com.psr.psr.user.dto.CheckNicknameReq
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

        /**
         * 로그인
         */
        @PostMapping("/login")
        @ResponseBody
        fun login (@RequestBody @Validated loginReq: LoginReq) : BaseResponse<TokenRes>{
                return BaseResponse(userService.login(loginReq))
        }

        /**
         * 닉네임 중복
         */
        @PostMapping("/nickname")
        @ResponseBody
        fun checkDuplicateNickname (@RequestBody @Validated nicknameReq: CheckNicknameReq) : BaseResponse<Boolean>{
                // 사용 가능 : True, 사용 불가 : False
                return BaseResponse(!userService.checkDuplicateNickname(nicknameReq.nickname))
        }
}