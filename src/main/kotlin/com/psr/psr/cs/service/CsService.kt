package com.psr.psr.cs.service

import com.psr.psr.cs.repository.FaqRepository
import com.psr.psr.cs.repository.NoticeRepository
import org.springframework.stereotype.Service

@Service
class CsService(
        private val faqRepository: FaqRepository,
        private val noticeRepository: NoticeRepository

) {

}