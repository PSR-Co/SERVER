package com.psr.psr.inquiry.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class InquiryReq (
    @field:NotBlank(message = "제목을 입력해주세요.")
    @field:Size(max = 100, message = "제목은 최대 100자입니다.")
    val title: String? = null,

    @field:NotBlank(message = "내용을 입력해주세요.")
    @field:Size(max = 250, message = "내용은 최대 250자입니다.")
    val content: String? = null
)