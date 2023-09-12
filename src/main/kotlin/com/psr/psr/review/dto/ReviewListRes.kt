package com.psr.psr.review.dto

import com.psr.psr.review.entity.Review
import java.time.format.DateTimeFormatter

data class ReviewListRes(
    val reviewId: Long,
    val rating: Int,
    val content: String,
    val imgList: List<String>?,
    val reviewedDate: String,
    val nickName: String,
    val profileImgUrl: String?
) {
    companion object {
        fun toDto(review: Review): ReviewListRes {
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
                profileImgUrl = review.order.user.imgUrl
            )
        }
    }
}
