package com.psr.psr.cs.service

import com.psr.psr.cs.dto.FaqListRes
import com.psr.psr.cs.dto.FaqRes
import com.psr.psr.cs.dto.NoticeListRes
import com.psr.psr.cs.dto.NoticeRes
import com.psr.psr.cs.dto.assembler.CsAssembler
import com.psr.psr.cs.entity.FaqType
import com.psr.psr.cs.repository.FaqRepository
import com.psr.psr.cs.repository.NoticeRepository
import com.psr.psr.global.Constant.USER_STATUS.USER_STATUS.ACTIVE_STATUS
import com.psr.psr.global.exception.BaseException
import com.psr.psr.global.exception.BaseResponseCode
import org.springframework.stereotype.Service

@Service
class CsService(
        private val faqRepository: FaqRepository,
        private val noticeRepository: NoticeRepository,
        private val csAssembler: CsAssembler

) {
        // 공지사항 메인
        fun getNotices() : NoticeListRes{
                return csAssembler.toNoticeListRes(noticeRepository.findByOrderByCreatedAtDesc())
        }

        // 공지사항 상세
        fun getNotice(noticeId: Long) : NoticeRes{
                val notice = noticeRepository.findByIdAndStatus(noticeId, ACTIVE_STATUS) ?: throw BaseException(BaseResponseCode.NOT_FOUND_NOTICE)
                return csAssembler.toNoticeRes(notice)
        }

        // 자주 묻는 질문 메인
        fun getFaqs(type: String?): FaqListRes {
                return if(type == null) csAssembler.toFaqListRes(faqRepository.findByOrderByCreatedAtDesc())
                else{
                        csAssembler.toFaqListRes(faqRepository.findByTypeOrderByCreatedAtDesc(FaqType.getTypeByName(type)))
                }
        }

        // 자주 묻는 질문 상세
        fun getFaq(faqId: Long): FaqRes {
                val faq = faqRepository.findByIdAndStatus(faqId, ACTIVE_STATUS) ?: throw BaseException(BaseResponseCode.NOT_FOUND_FAQ)
                return csAssembler.toFaqRes(faq)
        }

}