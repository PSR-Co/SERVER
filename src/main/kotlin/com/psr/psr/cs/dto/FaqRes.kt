package com.psr.psr.cs.dto

import com.fasterxml.jackson.annotation.JsonInclude

data class FaqRes (
    val faqId: Long,
    val type: String,
    val title: String,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val content: String?= null,
)