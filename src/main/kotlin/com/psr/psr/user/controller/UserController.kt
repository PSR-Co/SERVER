package com.psr.psr.user.controller

import com.psr.psr.global.Constant.UserStatus.UserStatus.INACTIVE_STATUS
import com.psr.psr.global.Constant.UserStatus.UserStatus.LOGOUT
import com.psr.psr.global.dto.BaseResponse
import com.psr.psr.global.exception.BaseException
import com.psr.psr.global.exception.BaseResponseCode
import com.psr.psr.global.jwt.UserAccount
import com.psr.psr.global.jwt.dto.TokenDto
import com.psr.psr.user.dto.*
import com.psr.psr.user.dto.request.*
import com.psr.psr.user.dto.response.EmailRes
import com.psr.psr.user.dto.response.MyPageInfoRes
import com.psr.psr.user.dto.response.ProfileRes
import com.psr.psr.user.service.UserService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.util.StringUtils
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
        fun signUp (@RequestBody @Validated signUpReq: SignUpReq) : BaseResponse<TokenDto>{
               return BaseResponse(userService.signUp(signUpReq))
        }

        /**
         * 로그인
         */
        @PostMapping("/login")
        fun login (@RequestBody @Validated loginReq: LoginReq) : BaseResponse<TokenDto>{
                return BaseResponse(userService.login(loginReq))
        }

        /**
         * 닉네임 중복
         */
        @PostMapping("/nickname")
        fun checkDuplicateNickname (@RequestBody @Validated nicknameReq: CheckNicknameReq) : BaseResponse<Boolean>{
                // 사용 가능 : True, 사용 불가 : False
                return BaseResponse(!userService.checkDuplicateNickname(nicknameReq.nickname))
        }

        /**
         * 사용자 프로필 불러오기
         */
        @GetMapping("/profile")
        fun getProfile(@AuthenticationPrincipal userAccount: UserAccount) : BaseResponse<ProfileRes>{
                return BaseResponse(userService.getProfile(userAccount.getUser()))
        }

        /**
         * 사용자 프로필 변경하기
         */
        @PostMapping("/profile")
        fun postProfile(@AuthenticationPrincipal userAccount: UserAccount, @RequestBody @Validated profileReq: ProfileReq) : BaseResponse<Any> {
                userService.postProfile(userAccount.getUser(), profileReq)
                return BaseResponse(BaseResponseCode.SUCCESS)
        }

        /**
         * 로그아웃
         */
        @PatchMapping("/logout")
        fun logout(@AuthenticationPrincipal userAccount: UserAccount, request: HttpServletRequest) : BaseResponse<Any> {
                userService.blackListToken(userAccount.getUser(), request, LOGOUT)
                return BaseResponse(BaseResponseCode.SUCCESS)
        }

        /**
         * 회원 탈퇴
         */
        @DeleteMapping("/signout")
        fun signOut(@AuthenticationPrincipal userAccount: UserAccount, request: HttpServletRequest) : BaseResponse<Any> {
                userService.blackListToken(userAccount.getUser(), request, INACTIVE_STATUS)
                userService.signOut(userAccount.getUser())
                return BaseResponse(BaseResponseCode.SUCCESS)
        }

        /**
         * 사업자 등록 번호 인증
         */
        @PostMapping("/eid")
        fun validateEid(@RequestBody @Validated userEidReq: UserEidReq) : BaseResponse<Boolean> {
                userService.validateEid(userEidReq)
                return BaseResponse(true)
        }

        /**
         * 마이페이지 내 정보 화면
         */
        @GetMapping("/mypage")
        fun getMyPageInfo(@AuthenticationPrincipal userAccount: UserAccount) : BaseResponse<MyPageInfoRes>{
               return BaseResponse(userService.getMyPageInfo(userAccount.getUser()))
        }

        /**
         * 토큰 재발급
         */
        @PostMapping("/reissue")
        fun reissueToken(@RequestBody @Validated tokenDto: TokenDto) : BaseResponse<TokenDto>{
                return BaseResponse(userService.reissueToken(tokenDto))
        }

        /**
         * 비밀번호 변경화면 with Token
         */
        @PatchMapping("/password-change")
        fun changePassword(@AuthenticationPrincipal userAccount: UserAccount, @RequestBody @Validated passwordReq: ChangePasswordReq) : BaseResponse<Any>{
                userService.changePassword(userAccount.getUser(), passwordReq)
                return BaseResponse(BaseResponseCode.SUCCESS)
        }

        /**
         * 비밀번호 재설정 except Token
         */
        @PatchMapping("/password-reset")
        fun resetPassword(@RequestBody @Validated resetPasswordReq: ResetPasswordReq) : BaseResponse<Any>{
                userService.resetPassword(resetPasswordReq)
                return BaseResponse(BaseResponseCode.SUCCESS)
        }

        /**
         * 관심 목록 변경
         */
        @PatchMapping("/watchlist")
        fun patchWatchLists(@AuthenticationPrincipal userAccount: UserAccount, @RequestBody @Validated userInterestListReq: UserInterestListDto) : BaseResponse<Any>{
                userService.patchWatchLists(userAccount.getUser(), userInterestListReq)
                return BaseResponse(BaseResponseCode.SUCCESS)
        }

        /**
         * 관심 목록 조회
         */
        @GetMapping("/watchlist")
        fun getWatchList(@AuthenticationPrincipal userAccount: UserAccount) : BaseResponse<UserInterestListDto>{
                return BaseResponse(userService.getWatchList(userAccount.getUser()))
        }

        /**
         * 휴대폰번호 유효
         */
        @PostMapping("/phone/check")
        fun checkValidPhone(@RequestBody @Validated validPhoneReq: ValidPhoneReq) : BaseResponse<Any>{
                userService.checkValidPhone(validPhoneReq)
                return BaseResponse(BaseResponseCode.SUCCESS)
        }

        /**
         * 휴대폰 인증번호 조회
         */
        @PostMapping("/phone/validation")
        fun checkValidSmsKey(@RequestBody @Validated validPhoneReq: ValidPhoneReq) : BaseResponse<Any>{
                userService.checkValidSmsKey(validPhoneReq.phone, validPhoneReq.smsKey!!)
                return BaseResponse(BaseResponseCode.SUCCESS)
        }

        /**
         * 아이디 + 인증번호 조회
         */
        @PostMapping("/email/search")
        fun findEmailSearch(@RequestBody @Validated findIdPwReq: FindIdPwReq): BaseResponse<EmailRes>{
                if(!StringUtils.hasText(findIdPwReq.name)) throw BaseException(BaseResponseCode.NOT_EMPTY_NAME)
                return BaseResponse(userService.findEmailSearch(findIdPwReq))
        }

        /**
         * 비밀번호 변경 + 인증번호 조회
         */
        @PostMapping("/password")
        fun findPWSearch(@RequestBody @Validated findIdPwReq: FindIdPwReq): BaseResponse<Any>{
                if(!StringUtils.hasText(findIdPwReq.email)) throw BaseException(BaseResponseCode.NOT_EMPTY_EMAIL)
                userService.findPWSearch(findIdPwReq)
                return BaseResponse(BaseResponseCode.SUCCESS)
        }

        /**
         * 마이페이지 알림 수신 여부
         */
        @PostMapping("/notification")
        fun postNotiStatus(@AuthenticationPrincipal userAccount: UserAccount): BaseResponse<Any>{
                return BaseResponse(userService.postNotiStatus(userAccount.getUser()))
        }
}