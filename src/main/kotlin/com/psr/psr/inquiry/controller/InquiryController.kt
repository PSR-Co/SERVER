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
                description = "제목을 입력해주세요.<br>" +
                        "제목은 최대 100자입니다.<br>" +
                        "내용을 입력해주세요.<br>" +
                        "내용은 최대 250자입니다.",
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
    @Operation(summary = "문의하기 상세 조회(박서연)", description = "문의 내역 상세조회를 한다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
            ApiResponse(
                responseCode = "404",
                description = "해당 문의를 찾을 수 없습니다.",
                content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
            )]
    )
    @GetMapping("/{inquiryId}")
    fun getInquiryDetails(
        @AuthenticationPrincipal userAccount: UserAccount,
        @Parameter(description = "(Long) 문의 Id", example = "1") @PathVariable inquiryId: Long
    ): BaseResponse<InquiryRes> {
        return BaseResponse(inquiryService.getInquiryDetails(userAccount.getUser(), inquiryId))
    }

    // 문의하기 목록 조회
    @Operation(summary = "문의하기 목록 조회(박서연)", description = "문의 내역 목록을 조회한다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
            ApiResponse(
                responseCode = "400",
                description = "올바르지 않은 문의 상태입니다.",
                content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
            )]
    )
    @GetMapping
    fun getInquiryList(
        @AuthenticationPrincipal userAccount: UserAccount,
        @Parameter(description = "(String) 문의 상태", example = "진행중/완료") @RequestParam status: String
    ): BaseResponse<InquiryListRes?> {
        return BaseResponse(inquiryService.getInquiryList(userAccount.getUser(), status))
    }

    // 문의하기 답변 등록
    @Operation(summary = "문의하기 답변 등록(박서연)", description = "문의하기의 답변을 등록한다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
            ApiResponse(
                responseCode = "400",
                description = "답변을 입력해주세요.<br>" +
                        "답변은 최대 250자입니다.",
                content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
            ),
            ApiResponse(
                responseCode = "403",
                description = "권한이 없습니다.",
                content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
            ),
            ApiResponse(
                responseCode = "404",
                description = "해당 문의를 찾을 수 없습니다.",
                content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
            ),
            ApiResponse(
                responseCode = "409",
                description = "이미 답변 완료된 문의입니다.",
                content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
            )]
    )
    @PatchMapping("/{inquiryId}")
    fun answerInquiry(
        @AuthenticationPrincipal userAccount: UserAccount,
        @Parameter(description = "(Long) 문의 Id", example = "1") @PathVariable inquiryId: Long,
        @RequestBody @Valid inquiryAnswerReq: InquiryAnswerReq
    ): BaseResponse<Unit> {
        return BaseResponse(inquiryService.answerInquiry(userAccount.getUser(), inquiryId, inquiryAnswerReq))
    }

    // 문의하기 삭제
    @Operation(summary = "문의하기 삭제(박서연)", description = "문의내역을 삭제한다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
            ApiResponse(
                responseCode = "403",
                description = "권한이 없습니다.",
                content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
            ),
            ApiResponse(
                responseCode = "404",
                description = "해당 문의를 찾을 수 없습니다.",
                content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
            )]
    )
    @DeleteMapping("/{inquiryId}")
    fun deleteInquiry(
        @AuthenticationPrincipal userAccount: UserAccount,
        @Parameter(description = "(Long) 문의 Id", example = "1") @PathVariable inquiryId: Long
    ): BaseResponse<Unit> {
        return BaseResponse(inquiryService.deleteInquiry(userAccount.getUser(), inquiryId))
    }
}
