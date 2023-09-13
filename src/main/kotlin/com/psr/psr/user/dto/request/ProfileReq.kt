package com.psr.psr.user.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class ProfileReq(
    @field:NotBlank
    @field:Pattern(
        regexp = "^([a-zA-Z0-9ㄱ-ㅎ|ㅏ-ㅣ|가-힣]).{0,10}\$",
        message = "한글, 영어, 숫자만 입력해주세요. (10글자)"
    )
    val nickname: String,
    val imgUrl: String
)