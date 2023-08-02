package com.psr.psr.inquiry.service

import com.psr.psr.global.Constant.USER_STATUS.USER_STATUS.ACTIVE_STATUS
import com.psr.psr.global.exception.BaseException
import com.psr.psr.global.exception.BaseResponseCode
import com.psr.psr.inquiry.dto.InquiryReq
import com.psr.psr.inquiry.dto.InquiryRes
import com.psr.psr.inquiry.entity.Inquiry
import com.psr.psr.inquiry.repository.InquiryRepository
import com.psr.psr.user.entity.User
import org.springframework.stereotype.Service

@Service
class InquiryService(
        private val inquiryRepository: InquiryRepository
) {
    // 문의 등록
    fun makeInquiry(user: User, inquiryReq: InquiryReq) {
        inquiryRepository.save(inquiryReq.toEntity(user))
    }

    // 문의 상세 조회
    fun getInquiryDetails(user: User, inquiryId: Long): InquiryRes {
        val inquiry: Inquiry = inquiryRepository.findByIdAndStatus(inquiryId, ACTIVE_STATUS)
            ?: throw BaseException(BaseResponseCode.NOT_FOUND_INQUIRY)
        return inquiry.toDto()
    }
}