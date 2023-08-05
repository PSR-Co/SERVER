package com.psr.psr.inquiry.dto

import com.fasterxml.jackson.annotation.JsonInclude

data class InquiryRes (
    val inquiryId: Long,
    val title: String,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val content: String?,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val answer: String?
)
