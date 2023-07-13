package com.psr.psr.inquiry.repository

import com.psr.psr.inquiry.entity.Inquiry
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface InquiryRepository: JpaRepository<Inquiry, Long> {
}