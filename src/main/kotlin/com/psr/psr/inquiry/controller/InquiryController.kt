package com.psr.psr.inquiry.controller

import com.psr.psr.global.dto.BaseResponse
import com.psr.psr.global.jwt.UserAccount
import com.psr.psr.inquiry.dto.InquiryListRes
import com.psr.psr.inquiry.dto.InquiryReq
import com.psr.psr.inquiry.dto.InquiryRes
import com.psr.psr.inquiry.service.InquiryService
import jakarta.validation.Valid
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/inquiries")
class InquiryController(
        private val inquiryService: InquiryService
){
        // 문의하기 등록
        @PostMapping
        fun makeInquiry (@AuthenticationPrincipal userAccount: UserAccount, @RequestBody @Valid inquiryReq: InquiryReq) : BaseResponse<Unit> {
                return BaseResponse(inquiryService.makeInquiry(userAccount.getUser(), inquiryReq))
        }

        // 문의하기 상세 조회
        @GetMapping("/{inquiryId}")
        fun getInquiryDetails (@AuthenticationPrincipal userAccount: UserAccount, @PathVariable inquiryId: Long
        ) : BaseResponse<InquiryRes> {
                return BaseResponse(inquiryService.getInquiryDetails(userAccount.getUser(), inquiryId))
        }

        // 문의하기 목록 조회
        @GetMapping
        fun getInquiryList (@AuthenticationPrincipal userAccount: UserAccount, @RequestParam status: String) : BaseResponse<List<InquiryListRes>> {
                return BaseResponse(inquiryService.getInquiryList(userAccount.getUser(), status))
        }
}
