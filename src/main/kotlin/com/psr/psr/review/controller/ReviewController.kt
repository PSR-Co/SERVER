package com.psr.psr.review.controller

import com.psr.psr.global.dto.BaseResponse
import com.psr.psr.global.jwt.UserAccount
import com.psr.psr.review.dto.ReviewReq
import com.psr.psr.review.service.ReviewService
import jakarta.validation.Valid
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
}