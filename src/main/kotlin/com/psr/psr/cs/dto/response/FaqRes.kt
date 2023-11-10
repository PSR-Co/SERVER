package com.psr.psr.cs.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.psr.psr.cs.entity.Faq
import io.swagger.v3.oas.annotations.media.Schema

data class FaqRes (
    @Schema(type = "Long", description = "자주 묻는 질문 id", example = "1")
    val faqId: Long,
    @Schema(type = "String", description = "자주 묻는 질문 타입", example = "계정관리", allowableValues = ["계정관리", "컨설팅", "상품"])
    val type: String,
    @Schema(type = "String", description = "자주 묻는 질문 제목", example = "로그아웃이 안돼요.")
    val title: String,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(type = "String", description = "자주 묻는 질문 내용", example = "설정에 들어가면 됩니다.")
    val content: String?= null,
){
    companion object{
        fun toFaqRes(faq: Faq): FaqRes {
            return FaqRes(faq.id, faq.type.value, faq.title, faq.content)
        }
    }
}