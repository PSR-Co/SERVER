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
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.util.StringUtils
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
@SecurityRequirement(name = "Bearer")
@Tag(name = "User", description = "회원 API")
class UserController(
        private val userService: UserService
) {
        /**
         * 회원가입
         */
        @Operation(summary = "[토큰 X] 회원가입(장채은)", description = "회원가입을 한다.")
        @ApiResponses(
                value = [
                        ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
                        ApiResponse(
                                responseCode = "400",
                                description = "올바르지 않은 이메일 형식입니다. <br> " +
                                        "비밀번호를 숫자, 문자, 특수문자 포함 8~15자리 이내로 입력해주세요. <br>" +
                                        "올바르지 않은 휴대폰 형식입니다.<br>" +
                                        "사용자 관심 주제는 1개이상, 3개 이하여야하며, 중복된 값이 포함되어 있지 않아야 합니다<br>" +
                                        "닉네임은 한글, 영어, 숫자만 입력해주세요. (10글자)<br>" +
                                        "사업자 정보를 입력해주세요.<br>" +
                                        "관리자는 한 명만 가능합니다.<br>" +
                                        "올바르지 않은 사용자 역할니다.<br>" +
                                        "variable + 을(를) 입력해주세요.",
                                content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
                        ),
                        ApiResponse(
                                responseCode = "409",
                                description = "이미 가입되어 있는 닉네임입니다.<br>" +
                                        "이미 가입되어 있는 이메일입니다.<br>" +
                                        "이미 가입되어 있는 휴대폰 번호입니다.",
                                content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
                        )]
        )
        @PostMapping("/signup")
        fun signUp (@RequestBody @Validated signUpReq: SignUpReq) : BaseResponse<TokenDto>{
               return BaseResponse(userService.signUp(signUpReq))
        }

        /**
         * 로그인
         */
        @Operation(summary = "[토큰 X] 로그인(장채은)", description = "로그인을 한다.")
        @ApiResponses(
                value = [
                        ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
                        ApiResponse(
                                responseCode = "400",
                                description = "올바르지 않은 이메일 형식입니다.<br> " +
                                        "비밀번호를 숫자, 문자, 특수문자 포함 8~15자리 이내로 입력해주세요.<br>" +
                                        "올바르지 않은 휴대폰 형식입니다.<br>" +
                                        "해당 이메일로 가입한 사용자를 찾을 수 없습니다.<br>" +
                                        "사용자의 비밀번호가 일치하지 않습니다.<br>" +
                                        "variable + 을(를) 입력해주세요.",
                                content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
                        )]
        )
        @PostMapping("/login")
        fun login (@RequestBody @Validated loginReq: LoginReq) : BaseResponse<TokenDto>{
                return BaseResponse(userService.login(loginReq))
        }

        /**
         * 닉네임 중복
         */
        @Operation(summary = "[토큰 X] 닉네임 중복 확인 (장채은)", description = "닉네임 중복을 확인 한다. 사용 가능 : True, 사용 불가 : False")
        @ApiResponses(
                value = [
                        ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
                        ApiResponse(
                                responseCode = "400",
                                description = "닉네임은 한글, 영어, 숫자만 입력해주세요. (10글자)<br>" +
                                        "variable + 을(를) 입력해주세요.",
                                content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
                        )]
        )
        @PostMapping("/nickname")
        fun checkDuplicateNickname (@RequestBody @Validated nicknameReq: CheckNicknameReq) : BaseResponse<Boolean>{
                // 사용 가능 : True, 사용 불가 : False
                return BaseResponse(!userService.checkDuplicateNickname(nicknameReq.nickname))
        }

        /**
         * 사용자 프로필 조회
         */
        @Operation(summary = "사용자 프로필 조회 (장채은)", description = "사용자 프로필을 불러온다.")
        @ApiResponses(
                value = [
                        ApiResponse(responseCode = "200", description = "요청에 성공했습니다.")]
        )
        @GetMapping("/profile")
        fun getProfile(@AuthenticationPrincipal userAccount: UserAccount) : BaseResponse<ProfileRes>{
                return BaseResponse(userService.getProfile(userAccount.getUser()))
        }

        /**
         * 사용자 프로필 변경하기
         */
        @Operation(summary = "사용자 프로필 변경 (장채은)", description = "사용자 프로필을 변경한다.")
        @ApiResponses(
                value = [
                        ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
                        ApiResponse(
                                responseCode = "409",
                                description = "이미 가입되어 있는 닉네임입니다.<br>",
                                content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
                        )]
        )
        @PatchMapping("/profile")
        fun patchProfile(@AuthenticationPrincipal userAccount: UserAccount, @RequestBody @Validated profileReq: ProfileReq) : BaseResponse<Any> {
                userService.patchProfile(userAccount.getUser(), profileReq)
                return BaseResponse(BaseResponseCode.SUCCESS)
        }

        /**
         * 로그아웃
         */
        @Operation(summary = "로그아웃 (장채은)", description = "로그아웃을 한다.")
        @PatchMapping("/logout")
        fun logout(@AuthenticationPrincipal userAccount: UserAccount, request: HttpServletRequest) : BaseResponse<Any> {
                userService.blackListToken(userAccount.getUser(), request, LOGOUT)
                return BaseResponse(BaseResponseCode.SUCCESS)
        }

        /**
         * 회원 탈퇴
         */
        @Operation(summary = "회원탈퇴 (장채은)", description = "회원 탈퇴를 한다.")
        @DeleteMapping("/signout")
        fun signOut(@AuthenticationPrincipal userAccount: UserAccount, request: HttpServletRequest) : BaseResponse<Any> {
                userService.blackListToken(userAccount.getUser(), request, INACTIVE_STATUS)
                userService.signOut(userAccount.getUser())
                return BaseResponse(BaseResponseCode.SUCCESS)
        }

        /**
         * 사업자 등록 번호 인증
         */
        @Operation(summary = "사업자 등록 번호 인증 (장채은)", description = "사업자 등록 번호를 인증한다.")
        @PostMapping("/eid")
        fun validateEid(@RequestBody @Validated userEidReq: UserEidReq) : BaseResponse<Boolean> {
                userService.validateEid(userEidReq)
                return BaseResponse(true)
        }

        /**
         * 마이페이지 내 정보 화면
         */
        @Operation(summary = "마이페이지 정보 확인 (장채은)", description = "마이페이지 내 정보를 확인한다.")
        @GetMapping("/mypage")
        fun getMyPageInfo(@AuthenticationPrincipal userAccount: UserAccount) : BaseResponse<MyPageInfoRes>{
               return BaseResponse(userService.getMyPageInfo(userAccount.getUser()))
        }

        /**
         * 토큰 재발급
         */
        @Operation(summary = "토큰 재발급 (장채은)", description = "토큰을 재발급한다.")
        @PostMapping("/reissue")
        fun reissueToken(@RequestBody @Validated tokenDto: TokenDto) : BaseResponse<TokenDto>{
                return BaseResponse(userService.reissueToken(tokenDto))
        }

        /**
         * 비밀번호 변경화면 with Token
         */
        @Operation(summary = "비밀번호 변경 - Token (장채은)", description = "토큰을 헤더에 넣어 비밀번호를 변경한다.")
        @PatchMapping("/password-change")
        fun changePassword(@AuthenticationPrincipal userAccount: UserAccount, @RequestBody @Validated passwordReq: ChangePasswordReq) : BaseResponse<Any>{
                userService.changePassword(userAccount.getUser(), passwordReq)
                return BaseResponse(BaseResponseCode.SUCCESS)
        }

        /**
         * 비밀번호 재설정 except Token
         */
        @Operation(summary = "비밀번호 재설정 (장채은)", description = "비밀번호를 재설정한다.")
        @PatchMapping("/password-reset")
        fun resetPassword(@RequestBody @Validated resetPasswordReq: ResetPasswordReq) : BaseResponse<Any>{
                userService.resetPassword(resetPasswordReq)
                return BaseResponse(BaseResponseCode.SUCCESS)
        }

        /**
         * 관심 목록 변경
         */
        @Operation(summary = "관심 목록 변경 (장채은)", description = "관심 목록을 변경한다.")
        @PatchMapping("/watchlist")
        fun patchWatchLists(@AuthenticationPrincipal userAccount: UserAccount, @RequestBody @Validated userInterestListReq: UserInterestListDto) : BaseResponse<Any>{
                userService.patchWatchLists(userAccount.getUser(), userInterestListReq)
                return BaseResponse(BaseResponseCode.SUCCESS)
        }

        /**
         * 관심 목록 조회
         */
        @Operation(summary = "관심 목록 조회 (장채은)", description = "관심 목록을 조회한다.")
        @GetMapping("/watchlist")
        fun getWatchList(@AuthenticationPrincipal userAccount: UserAccount) : BaseResponse<UserInterestListDto>{
                return BaseResponse(userService.getWatchList(userAccount.getUser()))
        }

        /**
         * 휴대폰번호 전송
         */
        @Operation(summary = "휴대폰 번호 전송 (장채은)", description = "휴대폰 번호를 전송하여 인증 코드를 확인한다.")
        @PostMapping("/phone/check")
        fun checkValidPhone(@RequestBody @Validated validPhoneReq: ValidPhoneReq) : BaseResponse<Any>{
                userService.checkValidPhone(validPhoneReq)
                return BaseResponse(BaseResponseCode.SUCCESS)
        }

        /**
         * 회원가입을 위한 휴대폰번호 전송
         */
        @Operation(summary = "회원가입 휴대폰 번호 전송 (장채은)", description = "회원가입을 위해 휴대폰 번호를 전송하여 인증 코드를 확인한다.")
        @PostMapping("/phone/check/signup")
        fun checkValidPhoneForSignUp(@RequestBody @Validated validPhoneReq: ValidPhoneReq) : BaseResponse<Any>{
                // 이미 있는 휴대폰 번호인지 확인
                if(userService.checkDuplicatePhone(validPhoneReq.phone)) throw BaseException(BaseResponseCode.EXISTS_PHONE)
                // 휴대폰 번호 전송
                userService.checkValidPhone(validPhoneReq)
                return BaseResponse(BaseResponseCode.SUCCESS)
        }

        /**
         * 휴대폰 인증번호 조회
         */
        @Operation(summary = "휴대폰 번호 인증번호 조회 (장채은)", description = "인증코드를 확인하여 일치하는지 확인한다. 유효기간은 5분이다.")
        @PostMapping("/phone/validation")
        fun checkValidSmsKey(@RequestBody @Validated validPhoneReq: ValidPhoneReq) : BaseResponse<Any>{
                userService.checkValidSmsKey(validPhoneReq.phone, validPhoneReq.smsKey!!)
                return BaseResponse(BaseResponseCode.SUCCESS)
        }

        /**
         * 아이디 + 인증번호 조회
         */
        @Operation(summary = "아이디 찾기를 위한 인증번호 조회 (장채은)", description = "휴대폰 번호로 휴대폰 인증번호 일치를 조회한다.")
        @PostMapping("/email/search")
        fun findEmailSearch(@RequestBody @Validated findIdPwReq: FindIdPwReq): BaseResponse<EmailRes>{
                if(!StringUtils.hasText(findIdPwReq.name)) throw BaseException(BaseResponseCode.NOT_EMPTY_NAME)
                return BaseResponse(userService.findEmailSearch(findIdPwReq))
        }

        /**
         * 비밀번호 변경 + 인증번호 조회
         */
        @Operation(summary = "비밀번호 재설정을 위한 인증번호 조회  (장채은)", description = "이메일 + 휴대폰 번호로 휴대폰 인증번호 일치를 조회한다.")
        @PostMapping("/password")
        fun findPWSearch(@RequestBody @Validated findIdPwReq: FindIdPwReq): BaseResponse<Any>{
                if(!StringUtils.hasText(findIdPwReq.email)) throw BaseException(BaseResponseCode.NOT_EMPTY_EMAIL)
                userService.findPWSearch(findIdPwReq)
                return BaseResponse(BaseResponseCode.SUCCESS)
        }

        /**
         * 마이페이지 알림 수신 여부
         */
        @Operation(summary = "마이페이지 알림 수신 여부 (장채은)", description = "알림 수신 여부를 변경한다.")
        @PostMapping("/notification")
        fun postNotiStatus(@AuthenticationPrincipal userAccount: UserAccount): BaseResponse<Any>{
                return BaseResponse(userService.postNotiStatus(userAccount.getUser()))
        }
}