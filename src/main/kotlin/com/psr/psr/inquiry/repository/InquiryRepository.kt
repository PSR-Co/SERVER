package com.psr.psr.inquiry.repository

import com.psr.psr.inquiry.entity.Inquiry
import com.psr.psr.inquiry.entity.InquiryStatus
import com.psr.psr.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface InquiryRepository: JpaRepository<Inquiry, Long> {
    fun findByUserAndInquiryStatusAndStatus(user: User, inquiryStatus: InquiryStatus, status: String): List<Inquiry>?
    fun findByInquiryStatusAndStatus(inquiryStatus: InquiryStatus, status: String): List<Inquiry>?
}