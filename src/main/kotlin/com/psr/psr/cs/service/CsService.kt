package com.psr.psr.cs.service

import com.psr.psr.cs.dto.response.FaqListRes
import com.psr.psr.cs.dto.response.FaqRes
import com.psr.psr.cs.dto.response.NoticeListRes
import com.psr.psr.cs.dto.response.NoticeRes
import com.psr.psr.cs.entity.FaqType
import com.psr.psr.cs.repository.FaqRepository
import com.psr.psr.cs.repository.NoticeRepository
import com.psr.psr.global.Constant.UserStatus.UserStatus.ACTIVE_STATUS
import com.psr.psr.global.exception.BaseException
import com.psr.psr.global.exception.BaseResponseCode
import org.springframework.stereotype.Service

@Service
class CsService(
        private val faqRepository: FaqRepository,
        private val noticeRepository: NoticeRepository,

) {
        // 공지사항 메인
        fun getNotices() : NoticeListRes {
                return NoticeListRes.toNoticeListRes(noticeRepository.findByStatusOrderByCreatedAtDesc(ACTIVE_STATUS))
        }

        // 공지사항 상세
        fun getNotice(noticeId: Long) : NoticeRes {
                val notice = noticeRepository.findByIdAndStatus(noticeId, ACTIVE_STATUS) ?: throw BaseException(BaseResponseCode.NOT_FOUND_NOTICE)
                return NoticeRes.toNoticeRes(notice)
        }

        // 자주 묻는 질문 메인
        fun getFaqs(type: String?): FaqListRes {
                return if(type == null) FaqListRes.toFaqListRes(faqRepository.findByStatusOrderByCreatedAtDesc(ACTIVE_STATUS))
                else{
                        FaqListRes.toFaqListRes(faqRepository.findByTypeAndStatusOrderByCreatedAtDesc(FaqType.getTypeByName(type), ACTIVE_STATUS))
                }
        }

        // 자주 묻는 질문 상세
        fun getFaq(faqId: Long): FaqRes {
                val faq = faqRepository.findByIdAndStatus(faqId, ACTIVE_STATUS) ?: throw BaseException(BaseResponseCode.NOT_FOUND_FAQ)
                return FaqRes.toFaqRes(faq)
        }

        // 홈 화면 조회 - 공지사항
        fun getHomePage(): NoticeListRes {
                return NoticeListRes.toNoticeListResForHomePage(noticeRepository.findTop3ByStatusOrderByCreatedAtDesc(ACTIVE_STATUS))
        }
}