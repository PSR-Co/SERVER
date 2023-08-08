package com.psr.psr.order.dto

import com.fasterxml.jackson.annotation.JsonInclude

data class OrderListRes (
    val orderId: Long,
    val orderDate: String,
    val userName: String,
    val profileImgUrl: String?,
    val productId: Long,
    val productName: String,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val productImgUrl: String?,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val isReviewed: Boolean?
)