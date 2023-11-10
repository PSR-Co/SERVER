package com.psr.psr.user.dto.request

import com.psr.psr.global.resolver.EnumValid
import com.psr.psr.user.dto.UserInterestDto
import com.psr.psr.user.entity.Category
import com.psr.psr.user.entity.Type
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.annotation.Nullable
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern
import org.jetbrains.annotations.NotNull


data class SignUpReq (
    @field:NotBlank
    @field:Email(message = "올바르지 않은 이메일 형식입니다.")
    @Schema(type = "String", description = "이메일", example = "asdf@email.com", required = true)
    val email: String,
    @field:NotBlank
    @field:Pattern(
        regexp = "^.*(?=^.{8,15}\$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#\$%^&+=]).*\$",
        message = "비밀번호를 숫자, 문자, 특수문자 포함 8~15자리 이내로 입력해주세요"
    )
    @Schema(type = "String", description = "비밀번호", example = "asdf1234!", required = true)
    var password: String,
    @field:NotBlank
    @EnumValid(enumClass = Type::class, message = "올바르지 않은 사용자 역할니다.")
    @Schema(type = "String", description = "역할", example = "일반", required = true, allowableValues = ["일반", "사업자", "쇼호스트", "관리자"])
    val type: String,
    @field:Pattern(
        regexp = "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})\$",
        message = "올바르지 않은 휴대폰 형식입니다."
    )
    @Schema(type = "String", description = "휴대폰", example = "010-0000-0000", required = true)
    val phone: String,
    @field:Nullable
    @Schema(type = "String", description = "프로필 이미지", example = "htt://asdf.jpg")
    val imgUrl: String? = null,
    @field:NotBlank
    @field:Pattern(
        regexp = "^([a-zA-Z0-9ㄱ-ㅎ|ㅏ-ㅣ|가-힣]).{0,10}\$",
        message = "닉네임은 한글, 영어, 숫자만 입력해주세요. (10글자)"
    )
    @Schema(type = "String", description = "닉네임", example = "길동이", required = true)
    val nickname: String,
    @field:NotNull
    @Schema(type = "String", description = "이름", example = "홍길동", required = true)
    val name: String,
    @Schema(type = "Boolean", description = "마켓팅 동의", example = "true", required = true, allowableValues = ["true"])
    val marketing: Boolean,
    @field:NotNull
    @Schema(type = "Boolean", description = "알림 동의", example = "true", required = true, allowableValues = ["true", "false"])
    val notification: Boolean,
    @Schema(type = "String", description = "알림 토큰", example = "fcmtoken", required = true)
    val deviceToken: String? = null,
    @field:NotEmpty
    @Schema(type = "List", description = "관심 목록", required = true)
    val interestList: List<UserInterestDto>,
    @Schema(type = "List", description = "사업자 등록 정보", required = false)
    val entreInfo: UserEidReq?= null
    )