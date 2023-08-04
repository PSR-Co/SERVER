package com.psr.psr.cs.controller

import com.psr.psr.cs.dto.NoticeListRes
import com.psr.psr.cs.dto.NoticeRes
import com.psr.psr.cs.service.CsService
import com.psr.psr.global.dto.BaseResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
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
        fun getNotices(): BaseResponse<NoticeListRes>{
                return BaseResponse(csService.getNotices())
        }

        /**
         * 공지사항 상세
         */
        @GetMapping("/notices/{noticeIdx}")
        @ResponseBody
        fun getNotice(@PathVariable(name = "noticeIdx") noticeIdx: Long): BaseResponse<NoticeRes>{
                return BaseResponse(csService.getNotice(noticeIdx))
        }
}