package com.psr.psr.user.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class ProfileReq(
    @field:NotBlank
    @field:Pattern(
        regexp = "^([a-zA-Z0-9ㄱ-ㅎ|ㅏ-ㅣ|가-힣]).{0,10}\$",
        message = "한글, 영어, 숫자만 입력해주세요. (10글자)"
    )
    @Schema(type = "String", description = "닉네임", required = true, example = "길동이")
    val nickname: String,
    @Schema(type = "String", description = "프로필 이미지", example = "htt://asdf.jpg")
    val imgUrl: String
)