package com.psr.psr.cs.controller

import com.psr.psr.cs.dto.NoticesListRes
import com.psr.psr.cs.service.CsService
import com.psr.psr.global.dto.BaseResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/cs")
class CsController(
        private val csService: CsService
) {
        /**
         * 공지사항 메인
         */
        @GetMapping("/notices")
        @ResponseBody
        fun getNotices(): BaseResponse<NoticesListRes>{
                return BaseResponse(csService.getNotices())
        }
}