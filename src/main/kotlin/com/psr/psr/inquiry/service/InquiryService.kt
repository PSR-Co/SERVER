package com.psr.psr.inquiry.service

import com.psr.psr.global.Constant.UserStatus.UserStatus.ACTIVE_STATUS
import com.psr.psr.global.exception.BaseException
import com.psr.psr.global.exception.BaseResponseCode
import com.psr.psr.inquiry.dto.*
import com.psr.psr.inquiry.entity.Inquiry
import com.psr.psr.inquiry.entity.InquiryStatus
import com.psr.psr.inquiry.repository.InquiryRepository
import com.psr.psr.user.entity.Type
import com.psr.psr.user.entity.User
import org.springframework.stereotype.Service

@Service
class InquiryService(
    private val inquiryRepository: InquiryRepository
) {
    // 문의 등록
    fun makeInquiry(user: User, inquiryReq: InquiryReq) {
        inquiryRepository.save(Inquiry.toEntity(user, inquiryReq))
    }

    // 문의 상세 조회
    fun getInquiryDetails(user: User, inquiryId: Long): InquiryRes {
        val inquiry: Inquiry = inquiryRepository.findByIdAndStatus(inquiryId, ACTIVE_STATUS)
            ?: throw BaseException(BaseResponseCode.NOT_FOUND_INQUIRY)
        return InquiryRes.toDto(inquiry)
    }

    // 문의 목록 조회
    fun getInquiryList(user: User, status: String): InquiryListRes {
        val inquiryStatus: InquiryStatus = InquiryStatus.findByName(status)
        val inquiries: List<InquiryRes> = (
                // 관리자일 때 전체 조회
                if (user.type == Type.MANAGER)
                    inquiryRepository.findByInquiryStatusAndStatus(inquiryStatus, ACTIVE_STATUS)
                // 관리자 아닐 때 본인 것만 조회
                else
                    inquiryRepository.findByUserAndInquiryStatusAndStatus(user, inquiryStatus, ACTIVE_STATUS)
                )
            .map { inquiry: Inquiry -> InquiryRes.toTitleDto(inquiry) }

        return InquiryListRes.toDto(inquiries)
    }

    // 문의 답변 등록
    fun answerInquiry(user: User, inquiryId: Long, inquiryAnswerReq: InquiryAnswerReq) {
        if (user.type != Type.MANAGER) throw BaseException(BaseResponseCode.NO_PERMISSION)

        val inquiry: Inquiry = inquiryRepository.findByIdAndStatus(inquiryId, ACTIVE_STATUS)
            ?: throw BaseException(BaseResponseCode.NOT_FOUND_INQUIRY)
        if (inquiry.inquiryStatus == InquiryStatus.COMPLETED) throw BaseException(BaseResponseCode.INQUIRY_ANSWER_ALREADY_COMPLETE)

        inquiry.registerAnswer(inquiryAnswerReq.answer)
        inquiryRepository.save(inquiry)
    }

    // 문의 삭제
    fun deleteInquiry(user: User, inquiryId: Long) {
        val inquiry: Inquiry = inquiryRepository.findByIdAndStatus(inquiryId, ACTIVE_STATUS)
            ?: throw BaseException(BaseResponseCode.NOT_FOUND_INQUIRY)
        if (inquiry.user.id != user.id && user.type != Type.MANAGER) throw BaseException(BaseResponseCode.NO_PERMISSION)
        inquiryRepository.delete(inquiry)
    }
}