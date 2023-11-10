package com.psr.psr.cs.dto.response

import com.psr.psr.cs.entity.Faq
import io.swagger.v3.oas.annotations.media.Schema
import java.util.stream.Collectors

data class FaqListRes (
    @Schema(description = "자주 묻는 질문 리스트")
    val faqLists: List<FaqRes>?
){
    companion object{
        fun toFaqListRes(faqList: List<Faq>?) : FaqListRes {
            if(faqList!!.isEmpty()) return FaqListRes(null)
            return FaqListRes(faqList.stream().map {
                    f -> FaqRes(f.id, f.type.value, f.title)
            }.collect(Collectors.toList()))
        }
    }
}