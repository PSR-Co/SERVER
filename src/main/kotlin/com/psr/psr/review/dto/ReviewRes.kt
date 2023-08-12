package com.psr.psr.review.dto

data class ReviewRes(
    val reviewId: Long,
    val rating: Int,
    val content: String,
    val imgList: List<String>?,
    val nickname: String,
    val productName: String,
    val productImgUrl: String?
)
