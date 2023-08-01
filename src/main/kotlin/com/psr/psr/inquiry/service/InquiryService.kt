package com.psr.psr.inquiry.service

import com.psr.psr.global.Constant.USER_STATUS.USER_STATUS.ACTIVE_STATUS
import com.psr.psr.global.exception.BaseException
import com.psr.psr.global.exception.BaseResponseCode
import com.psr.psr.inquiry.dto.InquiryListRes
import com.psr.psr.inquiry.dto.InquiryReq
import com.psr.psr.inquiry.entity.Inquiry
import com.psr.psr.inquiry.entity.InquiryStatus
import com.psr.psr.inquiry.repository.InquiryRepository
import com.psr.psr.user.entity.Type
import com.psr.psr.user.entity.User
import com.psr.psr.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class InquiryService(
        private val inquiryRepository: InquiryRepository,
        private val userRepository: UserRepository
) {
    fun makeInquiry(inquiryReq: InquiryReq, userId: Long) {
        val user: User = userRepository.findByIdOrNull(userId) ?: throw BaseException(BaseResponseCode.NOT_FOUND_USER)
        inquiryRepository.save(inquiryReq.toEntity(user))
    }

    // 문의 목록 조회
    fun getInquiryList(user: User, completed: Int): List<InquiryListRes> {
        val inquiryStatus: InquiryStatus = InquiryStatus.findByNum(completed)
        val inquiries: List<Inquiry> =
            if (user.type == Type.MANAGER)
                inquiryRepository.findByInquiryStatusAndStatus(inquiryStatus, ACTIVE_STATUS)
                    ?: throw BaseException(BaseResponseCode.NOT_FOUND_INQUIRY)
            else
                inquiryRepository.findByUserAndInquiryStatusAndStatus(user, inquiryStatus, ACTIVE_STATUS)
                    ?: throw BaseException(BaseResponseCode.NOT_FOUND_INQUIRY)

        return inquiries.map { inquiry: Inquiry -> inquiry.toListDto() }
    }
}