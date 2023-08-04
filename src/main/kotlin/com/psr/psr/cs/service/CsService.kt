package com.psr.psr.cs.service

import com.psr.psr.cs.dto.NoticesListRes
import com.psr.psr.cs.dto.assembler.CsAssembler
import com.psr.psr.cs.repository.FaqRepository
import com.psr.psr.cs.repository.NoticeRepository
import org.springframework.stereotype.Service

@Service
class CsService(
        private val faqRepository: FaqRepository,
        private val noticeRepository: NoticeRepository,
        private val csAssembler: CsAssembler

) {
        // 공지사항 메인
        fun getNotices() : NoticesListRes{
                return csAssembler.toNoticeListRes(noticeRepository.findByOrderByCreatedAtDesc())
        }

}