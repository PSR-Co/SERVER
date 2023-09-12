package com.psr.psr.inquiry.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.psr.psr.inquiry.entity.Inquiry

data class InquiryRes (
    val inquiryId: Long,
    val title: String,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val content: String?,
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
