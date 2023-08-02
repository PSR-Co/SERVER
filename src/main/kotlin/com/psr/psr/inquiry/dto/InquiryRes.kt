package com.psr.psr.inquiry.dto

import com.psr.psr.inquiry.entity.Inquiry

data class InquiryRes (
    val inquiryId: Long,
    val title: String,
    val content: String,
    val answer: String?
)
