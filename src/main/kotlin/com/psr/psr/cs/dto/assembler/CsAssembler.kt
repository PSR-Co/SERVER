package com.psr.psr.cs.dto.assembler

import com.psr.psr.cs.dto.FaqListRes
import com.psr.psr.cs.dto.FaqRes
import com.psr.psr.cs.dto.NoticeListRes
import com.psr.psr.cs.dto.NoticeRes
import com.psr.psr.cs.entity.Faq
import com.psr.psr.cs.entity.Notice
import org.springframework.stereotype.Component
import java.util.stream.Collectors

@Component
class CsAssembler {

    // 공지사항 메인
    fun toNoticeListRes(noticeList: List<Notice>?): NoticeListRes {
        if (noticeList == null) return NoticeListRes(null)
        return NoticeListRes(noticeList.stream().map {
            n -> NoticeRes(n.id, n.title, n.createdAt)
        }.collect(Collectors.toList()))
    }

    // 공지사항 상세
    fun toNoticeRes(notice: Notice): NoticeRes {
        return NoticeRes(notice.id, notice.title, notice.createdAt, notice.imgKey)
    }

    // 자주 묻는 질문 메인
    fun toFaqListRes(faqList: List<Faq>?) : FaqListRes {
        if(faqList == null) return FaqListRes(null)
        return FaqListRes(faqList.stream().map {
            f -> FaqRes(f.id, f.type.value, f.title)
        }.collect(Collectors.toList()))
    }

    // 자주 묻는 질문 상세
    fun toFaqRes(faq: Faq): FaqRes {
        return FaqRes(faq.id, faq.type.value, faq.title, faq.content)
    }
}