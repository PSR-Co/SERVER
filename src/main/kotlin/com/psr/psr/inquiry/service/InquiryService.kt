package com.psr.psr.inquiry.service

import com.psr.psr.global.exception.BaseException
import com.psr.psr.global.exception.BaseResponseCode
import com.psr.psr.inquiry.dto.InquiryReq
import com.psr.psr.inquiry.repository.InquiryRepository
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
}