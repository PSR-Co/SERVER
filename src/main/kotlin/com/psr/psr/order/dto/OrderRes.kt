package com.psr.psr.order.dto

data class OrderRes (
    val isSeller: Boolean,
    val status: String,
    val orderUserId: Long,
    val orderDate: String,
    val productId: Long,
    val productName: String,
    val ordererName: String,
    var websiteUrl: String? = null,
    val inquiry: String,
    val description: String
)