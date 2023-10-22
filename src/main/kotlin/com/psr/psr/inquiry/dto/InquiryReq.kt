package com.psr.psr.inquiry.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Schema(description = "문의 등록/수정")
data class InquiryReq (
    @field:NotBlank(message = "제목을 입력해주세요.")
    @field:Size(max = 100, message = "제목은 최대 100자입니다.")
    @Schema(description = "문의 제목", example = "문의 제목")
    val title: String? = null,

    @field:NotBlank(message = "내용을 입력해주세요.")
    @field:Size(max = 250, message = "내용은 최대 250자입니다.")
    @Schema(description = "문의 내용", example = "문의 내용")
    val content: String? = null
)