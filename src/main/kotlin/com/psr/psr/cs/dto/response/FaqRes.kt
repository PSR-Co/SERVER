package com.psr.psr.cs.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.psr.psr.cs.entity.Faq

data class FaqRes (
    val faqId: Long,
    val type: String,
    val title: String,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val content: String?= null,
){
    companion object{
        fun toFaqRes(faq: Faq): FaqRes {
            return FaqRes(faq.id, faq.type.value, faq.title, faq.content)
        }
    }
}