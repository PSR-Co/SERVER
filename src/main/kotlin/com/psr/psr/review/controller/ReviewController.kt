package com.psr.psr.review.controller

import com.psr.psr.global.dto.BaseResponse
import com.psr.psr.global.jwt.UserAccount
import com.psr.psr.review.dto.ReviewListRes
import com.psr.psr.review.dto.ReviewReq
import com.psr.psr.review.service.ReviewService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
class ReviewController(
    private val reviewService: ReviewService
) {
    // 리뷰 생성
    @PostMapping("/orders/{orderId}/review")
    fun makeReview(
        @AuthenticationPrincipal userAccount: UserAccount,
        @PathVariable orderId: Long,
        @RequestBody @Valid reviewReq: ReviewReq
    ): BaseResponse<Unit> {
        return BaseResponse(reviewService.makeReview(userAccount.getUser(), orderId, reviewReq))
    }

    // 리뷰 삭제
    @DeleteMapping("/reviews/{reviewId}")
    fun deleteReview(
        @AuthenticationPrincipal userAccount: UserAccount,
        @PathVariable reviewId: Long
    ): BaseResponse<Unit> {
        return BaseResponse(reviewService.deleteReview(userAccount.getUser(), reviewId))
    }

    // 상품별 리뷰 목록 조회
    @GetMapping("/products/{productId}/reviews")
    fun getProductReviews(
        @PathVariable productId: Long,
        @PageableDefault(size = 8, sort = ["id"], direction = Sort.Direction.DESC) pageable: Pageable
    ): BaseResponse<Page<ReviewListRes>> {
        return BaseResponse(reviewService.getProductReviews(productId, pageable))
    }
}