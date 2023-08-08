package com.psr.psr.product.dto.response

data class GetProductDetailRes(
    val isOwner: Boolean,
    val category: String,
    val imgList: List<String>,
    val userId: Long?,
    val nickname: String,
    val numOfLikes: Int,
    val name: String,
    val price: Int,
    val description: String,
    val isLike: Boolean
)
