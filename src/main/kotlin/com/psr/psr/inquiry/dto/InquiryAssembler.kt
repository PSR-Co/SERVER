package com.psr.psr.inquiry.dto

import com.psr.psr.inquiry.entity.Inquiry
import com.psr.psr.user.entity.User
import org.springframework.stereotype.Component

@Component
class InquiryAssembler {
    fun toEntity(user: User, inquiryReq: InquiryReq): Inquiry {
        return Inquiry(
            user = user,
            title = inquiryReq.title,
            content = inquiryReq.content
        )
    }

    fun toListDto(inquiryList: List<InquiryRes>): InquiryListRes {
        if (inquiryList.isEmpty()) return InquiryListRes(null)
        return InquiryListRes(inquiryList)
    }

    fun toDto(inquiry: Inquiry): InquiryRes {
        return InquiryRes(
            inquiryId = inquiry.id!!,
            title = inquiry.title!!,
            content = inquiry.content!!,
            answer = inquiry.answer
        )
    }

    fun toPrepareListDto(inquiry: Inquiry): InquiryRes {
        return InquiryRes(
            inquiryId = inquiry.id!!,
            title = inquiry.title!!,
            content = null,
            answer = null
        )
    }
}