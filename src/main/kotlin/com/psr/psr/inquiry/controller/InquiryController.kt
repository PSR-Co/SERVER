package com.psr.psr.inquiry.controller

import com.psr.psr.global.dto.BaseResponse
import com.psr.psr.global.jwt.UserAccount
import com.psr.psr.inquiry.dto.InquiryAnswerReq
import com.psr.psr.inquiry.dto.InquiryListRes
import com.psr.psr.inquiry.dto.InquiryReq
import com.psr.psr.inquiry.dto.InquiryRes
import com.psr.psr.inquiry.service.InquiryService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/inquiries")
@Tag(name = "Inquiry", description = "문의하기 API")
@SecurityRequirement(name = "Bearer")
class InquiryController(
    private val inquiryService: InquiryService
) {
    // 문의하기 등록
    @Operation(summary = "문의하기 등록(박서연)", description = "문의를 등록한다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
            ApiResponse(
                responseCode = "400",
                description = "제목을 입력해주세요. 제목은 최대 100자입니다. 내용을 입력해주세요. 내용은 최대 250자입니다.",
                content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
            )]
    )
    @PostMapping
    fun makeInquiry(
        @AuthenticationPrincipal userAccount: UserAccount,
        @RequestBody @Valid inquiryReq: InquiryReq
    ): BaseResponse<Unit> {
        return BaseResponse(inquiryService.makeInquiry(userAccount.getUser(), inquiryReq))
    }

    // 문의하기 상세 조회
    @GetMapping("/{inquiryId}")
    fun getInquiryDetails(
        @AuthenticationPrincipal userAccount: UserAccount,
        @Parameter(description = "(Long) 문의 Id", example = "1") @PathVariable inquiryId: Long
    ): BaseResponse<InquiryRes> {
        return BaseResponse(inquiryService.getInquiryDetails(userAccount.getUser(), inquiryId))
    }

    // 문의하기 목록 조회
    @GetMapping
    fun getInquiryList(
        @AuthenticationPrincipal userAccount: UserAccount,
        @RequestParam status: String
    ): BaseResponse<InquiryListRes?> {
        return BaseResponse(inquiryService.getInquiryList(userAccount.getUser(), status))
    }

    // 문의하기 답변 등록
    @PatchMapping("/{inquiryId}")
    fun answerInquiry(
        @AuthenticationPrincipal userAccount: UserAccount,
        @PathVariable inquiryId: Long,
        @RequestBody @Valid inquiryAnswerReq: InquiryAnswerReq
    ): BaseResponse<Unit> {
        return BaseResponse(inquiryService.answerInquiry(userAccount.getUser(), inquiryId, inquiryAnswerReq))
    }

    // 문의하기 답변 등록
    @DeleteMapping("/{inquiryId}")
    fun deleteInquiry(
        @AuthenticationPrincipal userAccount: UserAccount,
        @PathVariable inquiryId: Long
    ): BaseResponse<Unit> {
        return BaseResponse(inquiryService.deleteInquiry(userAccount.getUser(), inquiryId))
    }
}
