package com.psr.psr.inquiry.dto

data class InquiryListRes (
    val inquiries: List<InquiryRes>?
) {
    companion object {
        fun toDto(inquiryList: List<InquiryRes>): InquiryListRes {
            if (inquiryList.isEmpty()) return InquiryListRes(null)
            return InquiryListRes(inquiryList)
        }
    }
}
