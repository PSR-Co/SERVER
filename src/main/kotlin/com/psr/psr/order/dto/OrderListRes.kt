package com.psr.psr.order.dto

data class OrderListRes (
    val orderId: Long,
    val orderDate: String,
    val userName: String,
    val productId: Long,
    val productName: String,
    val productImgUrl: String?,
    val reviewId: Long?
)