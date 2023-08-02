package com.psr.psr.inquiry.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class InquiryAnswerReq (
    @field:NotBlank(message = "답변을 입력해주세요.")
    @field:Size(max = 250, message = "답변은 최대 250자입니다.")
    val answer: String
)