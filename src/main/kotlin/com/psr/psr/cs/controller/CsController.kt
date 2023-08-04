package com.psr.psr.cs.controller

import com.psr.psr.cs.dto.FaqListRes
import com.psr.psr.cs.dto.NoticeListRes
import com.psr.psr.cs.dto.NoticeRes
import com.psr.psr.cs.service.CsService
import com.psr.psr.global.dto.BaseResponse
import org.springframework.web.bind.annotation.*

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
        @GetMapping("/notices/{noticeId}")
        @ResponseBody
        fun getNotice(@PathVariable(name = "noticeId") noticeId: Long): BaseResponse<NoticeRes>{
                return BaseResponse(csService.getNotice(noticeId))
        }

        /**
         * 자주 묻는 질문 메인
         */
        @GetMapping("/faqs")
        @ResponseBody
        fun getFaqs(@RequestParam(value = "type", required = false) type: String?): BaseResponse<FaqListRes>{
                return BaseResponse(csService.getFaqs(type))
        }
}