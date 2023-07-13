package com.psr.psr.inquiry.controller

import com.psr.psr.inquiry.service.InquiryService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/inquiries")
class InquiryController(
        private val inquiryService: InquiryService
){
}
