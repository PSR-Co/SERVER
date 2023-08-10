package com.psr.psr.user.controller

import com.psr.psr.global.Constant.UserStatus.UserStatus.INACTIVE_STATUS
import com.psr.psr.global.Constant.UserStatus.UserStatus.LOGOUT
import com.psr.psr.global.dto.BaseResponse
import com.psr.psr.global.exception.BaseResponseCode
import com.psr.psr.global.jwt.UserAccount
import com.psr.psr.global.jwt.dto.TokenDto
import com.psr.psr.user.dto.*
import com.psr.psr.user.dto.response.MyPageInfoRes
import com.psr.psr.user.dto.response.ProfileRes
import com.psr.psr.user.dto.request.*
import com.psr.psr.user.service.UserService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.annotation.AuthenticationPrincipal
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
        fun signUp (@RequestBody @Validated signUpReq: SignUpReq) : BaseResponse<TokenDto>{
               return BaseResponse(userService.signUp(signUpReq))
        }

        /**
         * 로그인
         */
        @PostMapping("/login")
        @ResponseBody
        fun login (@RequestBody @Validated loginReq: LoginReq) : BaseResponse<TokenDto>{
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

        /**
         * 사용자 프로필 불러오기
         */
        @GetMapping("/profile")
        @ResponseBody
        fun getProfile(@AuthenticationPrincipal userAccount: UserAccount) : BaseResponse<ProfileRes>{
                return BaseResponse(userService.getProfile(userAccount.getUser()))
        }

        /**
         * 사용자 프로필 변경하기
         */
        @PostMapping("/profile")
        @ResponseBody
        fun postProfile(@AuthenticationPrincipal userAccount: UserAccount, @RequestBody @Validated profileReq: ProfileReq) : BaseResponse<Any> {
                userService.postProfile(userAccount.getUser(), profileReq)
                return BaseResponse(BaseResponseCode.SUCCESS)
        }

        /**
         * 로그아웃
         */
        @PatchMapping("/logout")
        @ResponseBody
        fun logout(@AuthenticationPrincipal userAccount: UserAccount, request: HttpServletRequest) : BaseResponse<Any> {
                userService.blackListToken(userAccount.getUser(), request, LOGOUT)
                return BaseResponse(BaseResponseCode.SUCCESS)
        }

        /**
         * 회원 탈퇴
         */
        @DeleteMapping("/signout")
        @ResponseBody
        fun signOut(@AuthenticationPrincipal userAccount: UserAccount, request: HttpServletRequest) : BaseResponse<Any> {
                userService.blackListToken(userAccount.getUser(), request, INACTIVE_STATUS)
                userService.signOut(userAccount.getUser())
                return BaseResponse(BaseResponseCode.SUCCESS)
        }

        /**
         * 사업자 등록 번호 인증
         */
        @PostMapping("/eid")
        @ResponseBody
        fun validateEid(@RequestBody @Validated userEidReq: UserEidReq) : BaseResponse<Boolean> {
                userService.validateEid(userEidReq)
                return BaseResponse(true)
        }

        /**
         * 마이페이지 내 정보 화면
         */
        @GetMapping("/mypage")
        @ResponseBody
        fun getMyPageInfo(@AuthenticationPrincipal userAccount: UserAccount) : BaseResponse<MyPageInfoRes>{
               return BaseResponse(userService.getMyPageInfo(userAccount.getUser()))
        }

        /**
         * 토큰 재발급
         */
        @PostMapping("/reissue")
        @ResponseBody
        fun reissueToken(@RequestBody @Validated tokenDto: TokenDto) : BaseResponse<TokenDto>{
                return BaseResponse(userService.reissueToken(tokenDto))
        }

        /**
         * 비밀번호 변경화면 with Token
         */
        @PatchMapping("/password-change")
        @ResponseBody
        fun changePassword(@AuthenticationPrincipal userAccount: UserAccount, @RequestBody @Validated passwordReq: ChangePasswordReq) : BaseResponse<Any>{
                userService.changePassword(userAccount.getUser(), passwordReq)
                return BaseResponse(BaseResponseCode.SUCCESS)
        }

        /**
         * 비밀번호 재설정 except Token
         */
        @PatchMapping("/password-reset")
        @ResponseBody
        fun resetPassword(@RequestBody @Validated resetPasswordReq: ResetPasswordReq) : BaseResponse<Any>{
                userService.resetPassword(resetPasswordReq)
                return BaseResponse(BaseResponseCode.SUCCESS)
        }

        /**
         * 관심 목록 변경
         */
        @PatchMapping("/watchlists")
        @ResponseBody
        fun patchWatchLists(@AuthenticationPrincipal userAccount: UserAccount, @RequestBody @Validated userInterestListReq: UserInterestListReq) : BaseResponse<Any>{
                userService.patchWatchLists(userAccount.getUser(), userInterestListReq)
                return BaseResponse(BaseResponseCode.SUCCESS)
        }

        /**
         * 휴대폰번호 유효
         */
        @PostMapping("/phone/check")
        @ResponseBody
        fun checkValidPhone(@RequestBody @Validated validPhoneReq: ValidPhoneReq) : BaseResponse<Any>{
                userService.checkValidPhone(validPhoneReq)
                return BaseResponse(BaseResponseCode.SUCCESS)
        }

        /**
         * 휴대폰 인증번호 조회
         */
        @PostMapping("/phone/validation")
        @ResponseBody
        fun checkValidSmsKey(@RequestBody @Validated validPhoneReq: ValidPhoneReq) : BaseResponse<Any>{
                userService.checkValidSmsKey(validPhoneReq)
                return BaseResponse(BaseResponseCode.SUCCESS)
        }
}