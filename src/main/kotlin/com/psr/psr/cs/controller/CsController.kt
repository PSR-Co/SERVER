package com.psr.psr.cs.controller

import com.psr.psr.cs.dto.response.FaqListRes
import com.psr.psr.cs.dto.response.FaqRes
import com.psr.psr.cs.dto.response.NoticeListRes
import com.psr.psr.cs.dto.response.NoticeRes
import com.psr.psr.cs.service.CsService
import com.psr.psr.global.dto.BaseResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/cs")
@Tag(name = "CS", description = "CS API")
@SecurityRequirement(name = "Bearer")
class CsController(
        private val csService: CsService
) {
        /**
         * 공지사항 메인
         */
        @Operation(summary = "공지사항 메인(장채은)", description = "공지사항 리스트를 확인한다.")
        @ApiResponses(
                value = [
                        ApiResponse(responseCode = "200", description = "요청에 성공했습니다.")]
        )
        @GetMapping("/notices")
        fun getNotices(): BaseResponse<NoticeListRes>{
                return BaseResponse(csService.getNotices())
        }

        /**
         * 공지사항 상세
         */
        @Operation(summary = "공지사항 상세(장채은)", description = "공지사항 상세를 확인한다.")
        @ApiResponses(
                value = [
                        ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
                        ApiResponse(
                                responseCode = "404",
                                description = "해당 공지사항를 찾을 수 없습니다.",
                                content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
                        )         ]
        )
        @GetMapping("/notices/{noticeId}")
        fun getNotice(
                @Parameter(description = "(Long) 공지사항 Id", example = "1") @PathVariable(name = "noticeId") noticeId: Long): BaseResponse<NoticeRes>{
                return BaseResponse(csService.getNotice(noticeId))
        }

        /**
         * 자주 묻는 질문 메인
         */
        @Operation(summary = "자주 묻는 질문 메인(장채은)", description = "자주 묻는 질문 리스트를 확인한다.")
        @ApiResponses(
                value = [
                        ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
                        ApiResponse(
                                responseCode = "400",
                                description = "올바르지 않은 FAQ 카테고리입니다.",
                                content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
                        )
                ]
        )
        @GetMapping("/faqs")
        fun getFaqs(
                @Parameter(description = "(String) 자주 묻는 질문 타입 (계정관리/컨설팅/상품)", example = "계정관리") @RequestParam(value = "type", required = false) type: String?): BaseResponse<FaqListRes>{
                return BaseResponse(csService.getFaqs(type))
        }

        /**
         * 자주 묻는 질문 상세
         */
        @Operation(summary = "자주 묻는 질문 상세(장채은)", description = "자주 묻는 질문 상세를 확인한다.")
        @ApiResponses(
                value = [
                        ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
                        ApiResponse(
                                responseCode = "404",
                                description = "해당 FAQ를 찾을 수 없습니다.",
                                content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
                        )
                ]
        )
        @GetMapping("/faqs/{faqId}")
        fun getFaq(
                @Parameter(description = "(Long) 자주 묻는 질문 Id", example = "1") @PathVariable(name = "faqId") faqId: Long): BaseResponse<FaqRes>{
                return BaseResponse(csService.getFaq(faqId))
        }

        /**
         * 홈 화면 조회 - 공지사항
         */
        @Operation(summary = "홈 화면 조회 - 공지사항(박소정)", description = "홈 화면의 공지사항을 조회한다.")
        @ApiResponses(
                value = [
                        ApiResponse(responseCode = "200", description = "요청에 성공했습니다.")]
        )
        @GetMapping("/notices/home")
        fun getHomePage(): BaseResponse<NoticeListRes> {
                return BaseResponse(csService.getHomePage())
        }
}