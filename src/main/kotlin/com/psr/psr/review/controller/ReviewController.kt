package com.psr.psr.review.controller

import com.psr.psr.global.Constant.REPORT.REPORT.CATEGORY
import com.psr.psr.global.dto.BaseResponse
import com.psr.psr.global.exception.BaseResponseCode
import com.psr.psr.global.jwt.UserAccount
import com.psr.psr.review.dto.GetProductDetailRes
import com.psr.psr.review.dto.ReviewListRes
import com.psr.psr.review.dto.ReviewReq
import com.psr.psr.review.dto.ReviewRes
import com.psr.psr.review.service.ReviewService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@Tag(name = "Review", description = "리뷰 API")
@SecurityRequirement(name = "Bearer")
class ReviewController(
    private val reviewService: ReviewService
) {
    // 리뷰 생성
    @Operation(summary = "리뷰 등록(박서연)", description = "상품에 대한 리뷰를 등록한다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
            ApiResponse(
                responseCode = "400",
                description = "별점을 입력해주세요.<br>" +
                        "별점은 1~5점이여야 합니다.<br>" +
                        "내용을 입력해주세요.<br>" +
                        "내용은 최대 250자입니다.<br>" +
                        "imgUrl은 null 또는 1~5개이어야 합니다.",
                content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
            ),
            ApiResponse(
                responseCode = "403",
                description = "권한이 없습니다.",
                content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
            ),
            ApiResponse(
                responseCode = "404",
                description = "해당 요청을 찾을 수 없습니다.",
                content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
            ),
            ApiResponse(
                responseCode = "409",
                description = "이미 리뷰 완료된 요청입니다.<br>" +
                        "아직 완료된 요청이 아닙니다.",
                content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
            )]
    )
    @PostMapping("/orders/{orderId}/review")
    fun makeReview(
        @AuthenticationPrincipal userAccount: UserAccount,
        @Parameter(description = "(Long) 요청 Id", example = "1") @PathVariable orderId: Long,
        @RequestBody @Valid reviewReq: ReviewReq
    ): BaseResponse<Unit> {
        return BaseResponse(reviewService.makeReview(userAccount.getUser(), orderId, reviewReq))
    }

    // 리뷰 수정
    @Operation(summary = "리뷰 수정(박서연)", description = "상품에 대한 리뷰를 수정한다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
            ApiResponse(
                responseCode = "400",
                description = "별점을 입력해주세요.<br>" +
                        "별점은 1~5점이여야 합니다.<br>" +
                        "내용을 입력해주세요.<br>" +
                        "내용은 최대 250자입니다.<br>" +
                        "imgUrl은 null 또는 1~5개이어야 합니다.",
                content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
            ),
            ApiResponse(
                responseCode = "403",
                description = "권한이 없습니다.",
                content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
            ),
            ApiResponse(
                responseCode = "404",
                description = "해당 리뷰를 찾을 수 없습니다.",
                content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
            )]
    )
    @PatchMapping("/reviews/{reviewId}")
    fun editReview(
        @AuthenticationPrincipal userAccount: UserAccount,
        @Parameter(description = "(Long) 리뷰 Id", example = "1") @PathVariable reviewId: Long,
        @RequestBody @Valid reviewReq: ReviewReq
    ): BaseResponse<Unit> {
        return BaseResponse(reviewService.editReview(userAccount.getUser(), reviewId, reviewReq))
    }

    // 리뷰 삭제
    @Operation(summary = "리뷰 삭제(박서연)", description = "상품에 대한 리뷰를 삭제한다.")
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
                description = "해당 리뷰를 찾을 수 없습니다.",
                content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
            )]
    )
    @DeleteMapping("/reviews/{reviewId}")
    fun deleteReview(
        @AuthenticationPrincipal userAccount: UserAccount,
        @Parameter(description = "(Long) 리뷰 Id", example = "1")@PathVariable reviewId: Long
    ): BaseResponse<Unit> {
        return BaseResponse(reviewService.deleteReview(userAccount.getUser(), reviewId))
    }

    // 상품별 리뷰 목록 조회
    @Operation(summary = "상품별 리뷰 목록 조회(박서연)", description = "상품에 대한 리뷰 목록을 조회한다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
            ApiResponse(
                responseCode = "404",
                description = "해당 상품을 찾을 수 없습니다.",
                content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
            )]
    )
    @GetMapping("/products/{productId}/reviews")
    fun getProductReviews(
        @AuthenticationPrincipal userAccount: UserAccount,
        @Parameter(description = "(Long) 상품 Id", example = "1") @PathVariable productId: Long,
        @ParameterObject @PageableDefault(size = 8, sort = ["id"], direction = Sort.Direction.DESC) pageable: Pageable
    ): BaseResponse<Page<ReviewListRes>> {
        return BaseResponse(reviewService.getProductReviews(userAccount.getUser(), productId, pageable))
    }

    // 리뷰 개별 조회
    @Operation(summary = "리뷰 개별 조회(박서연)", description = "리뷰 개별 조회한다.")
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
                description = "해당 리뷰를 찾을 수 없습니다.",
                content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
            )]
    )
    @GetMapping("/reviews/{reviewId}")
    fun getReview(
        @AuthenticationPrincipal userAccount: UserAccount,
        @Parameter(description = "(Long) 리뷰 Id", example = "1")@PathVariable reviewId: Long
    ): BaseResponse<ReviewRes> {
        return BaseResponse(reviewService.getReview(userAccount.getUser(), reviewId))
    }

    // 리뷰 신고
    @Operation(summary = "리뷰 신고(박서연)", description = "리뷰를 신고한다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
            ApiResponse(
                responseCode = "400",
                description = "신고 카테고리를 입력해주세요.",
                content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
            ),
            ApiResponse(
                responseCode = "401",
                description = "본인이 작성한 리뷰는 신고할 수 없습니다.",
                content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
            ),
            ApiResponse(
                responseCode = "403",
                description = "권한이 없습니다.",
                content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
            ),
            ApiResponse(
                responseCode = "404",
                description = "해당 리뷰를 찾을 수 없습니다.",
                content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
            ),
            ApiResponse(
                responseCode = "409",
                description = "이미 신고 완료되었습니다.",
                content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
            )]
    )
    @PostMapping("/reviews/{reviewId}/report")
    fun reportReview(
        @AuthenticationPrincipal userAccount: UserAccount,
        @Parameter(description = "(Long) 리뷰 Id", example = "1") @PathVariable reviewId: Long,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = [Content(
                examples = [ExampleObject(
                    value = "{" +
                            "\"category\" : \"JUNK\"" +
                            "}"
                )], schema = Schema(title = "category", allowableValues = ["JUNK", "ABUSE", "PORN", "FRAUD", "NOT_FIT"])
            )]
        ) @RequestBody category: Map<String, String>
    ): BaseResponse<Unit> {
        category[CATEGORY] ?: return BaseResponse(BaseResponseCode.NULL_REPORT_CATEGORY)
        return BaseResponse(reviewService.reportReview(userAccount.getUser(), reviewId, category[CATEGORY]!!))
    }

    /**
     * 상품 상세 조회 - 리뷰
     */
    @Operation(summary = "상품 상세 조회 - 리뷰(박소정)", description = "상품 상세의 리뷰를 조회한다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "요청에 성공했습니다."),
            ApiResponse(
                responseCode = "404",
                description = "해당 상품을 찾을 수 없습니다.",
                content = arrayOf(Content(schema = Schema(implementation = BaseResponse::class)))
            )]
    )
    @GetMapping("products/{productId}/reviews/top")
    fun getProductDetail(@AuthenticationPrincipal userAccount: UserAccount,
                         @Parameter(description = "(Long) 상품 id", example = "1") @PathVariable productId: Long): BaseResponse<GetProductDetailRes> {
        return BaseResponse(reviewService.getProductDetail(userAccount.getUser(), productId));
    }
}