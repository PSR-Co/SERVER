package com.psr.psr.inquiry.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.psr.psr.inquiry.entity.Inquiry
import io.swagger.v3.oas.annotations.media.Schema

data class InquiryRes (
    @Schema(description = "문의 Id", example = "1")
    val inquiryId: Long,

    @Schema(description = "문의 제목", example = "문의 제목")
    val title: String,

    @Schema(description = "문의 내용", example = "문의 내용")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val content: String?,

    @Schema(description = "문의 답변", example = "문의 답변")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val answer: String?
){
    companion object {
        fun toDto(inquiry: Inquiry): InquiryRes {
            return InquiryRes(
                inquiryId = inquiry.id!!,
                title = inquiry.title!!,
                content = inquiry.content!!,
                answer = inquiry.answer
            )
        }

        fun toTitleDto(inquiry: Inquiry): InquiryRes {
            return InquiryRes(
                inquiryId = inquiry.id!!,
                title = inquiry.title!!,
                content = null,
                answer = null
            )
        }
    }
}
