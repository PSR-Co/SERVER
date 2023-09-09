package com.psr.psr.cs.dto.response

import com.psr.psr.cs.entity.Faq
import java.util.stream.Collectors

data class FaqListRes (
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