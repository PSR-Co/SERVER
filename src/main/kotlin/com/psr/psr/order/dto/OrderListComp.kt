package com.psr.psr.order.dto

data class OrderListComp (
    val orderId: Long,
    val orderDate: String,
    val userName: String,
    val productId: Long,
    val productName: String,
    val isReviewed: Boolean
)