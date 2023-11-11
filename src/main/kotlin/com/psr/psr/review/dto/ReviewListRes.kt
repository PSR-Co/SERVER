package com.psr.psr.review.dto

import com.psr.psr.review.entity.Review
import com.psr.psr.user.entity.User
import io.swagger.v3.oas.annotations.media.Schema
import java.time.format.DateTimeFormatter

data class ReviewListRes(
    @Schema(description = "리뷰 Id", example = "1")
    val reviewId: Long,
    @Schema(description = "별점", example = "5")
    val rating: Int,
    @Schema(description = "리뷰 내용", example = "좋아요")
    val content: String,
    @Schema(description = "리뷰 이미지 리스트")
    val imgList: List<String>?,
    @Schema(description = "리뷰 작성 일자", example = "2023-01-01")
    val reviewedDate: String,
    @Schema(description = "작성자 닉네임", example = "곤주")
    val nickName: String,
    @Schema(description = "작성자 프로필 이미지", example = "http~~")
    val profileImgUrl: String?,
    @Schema(description = "본인 작성 리뷰 여부")
    val isMyReview: Boolean
) {
    companion object {
        fun toDto(review: Review, user: User): ReviewListRes {
            val reviewImgs =
                if (review.imgs.isNotEmpty()) review.imgs.map { img -> img.imgUrl }
                else null
            return ReviewListRes(
                reviewId = review.id!!,
                rating = review.rating,
                content = review.content,
                imgList = reviewImgs,
                reviewedDate = review.createdAt.format(DateTimeFormatter.ISO_DATE),
                nickName = review.order.user.nickname,
                profileImgUrl = review.order.user.imgUrl,
                isMyReview = review.order.user == user
            )
        }
    }
}
