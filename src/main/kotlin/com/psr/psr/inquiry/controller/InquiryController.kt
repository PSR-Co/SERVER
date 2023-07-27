package com.psr.psr.inquiry.controller

import com.psr.psr.global.dto.BaseResponse
import com.psr.psr.inquiry.dto.InquiryReq
import com.psr.psr.inquiry.service.InquiryService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/inquiries")
class InquiryController(
        private val inquiryService: InquiryService
){
        // 문의하기 등록
        @PostMapping
        fun makeInquiry (@RequestBody @Valid inquiryReq: InquiryReq) : BaseResponse<Unit> {
                return BaseResponse(inquiryService.makeInquiry(inquiryReq, 1L))
        }
}
