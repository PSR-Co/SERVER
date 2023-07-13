package com.psr.psr.inquiry.service

import com.psr.psr.inquiry.repository.InquiryRepository
import org.springframework.stereotype.Service

@Service
class InquiryService(
        private val inquiryRepository: InquiryRepository
) {
}