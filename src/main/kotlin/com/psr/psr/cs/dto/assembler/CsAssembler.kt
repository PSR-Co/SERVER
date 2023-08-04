package com.psr.psr.cs.dto.assembler

import com.psr.psr.cs.dto.NoticesListRes
import com.psr.psr.cs.dto.NoticesRes
import com.psr.psr.cs.entity.Notice
import org.springframework.stereotype.Component
import java.util.*
import java.util.stream.Collectors

@Component
class CsAssembler {

    // 공지사항 메인
    fun toNoticeListRes(noticeList: List<Notice>?): NoticesListRes {
        if (noticeList == null) return NoticesListRes(null)
        return NoticesListRes(noticeList.stream().map {
            n -> NoticesRes(n.id, n.title, n.createdAt)
        }.collect(Collectors.toList()))
    }
}