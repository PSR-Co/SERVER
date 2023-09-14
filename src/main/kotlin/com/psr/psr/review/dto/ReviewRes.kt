package com.psr.psr.review.dto

import com.psr.psr.review.entity.Review

data class ReviewRes(
    val reviewId: Long,
    val rating: Int,
    val content: String,
    val imgList: List<String>?,
    val nickname: String,
    val productName: String,
    val productImgUrl: String?
) {
    companion object {
        fun toDto(review: Review): ReviewRes {
            val reviewImgs =
                if (review.imgs.isNotEmpty()) review.imgs.map { img -> img.imgUrl }
                else null
            return ReviewRes(
                reviewId = review.id!!,
                rating = review.rating,
                content = review.content,
                imgList = reviewImgs,
                nickname = review.product.user.nickname,
                productName = review.product.name,
                productImgUrl = review.product.imgs.firstOrNull()?.imgUrl
            )
        }
    }
}
