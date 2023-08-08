package com.psr.psr.review.dto

data class ReviewListRes(
    val reviewId: Long,
    val rating: Int,
    val content: String,
    val imgList: List<String>?,
    val reviewedDate: String,
    val nickName: String,
    val profileImgUrl: String?
)
