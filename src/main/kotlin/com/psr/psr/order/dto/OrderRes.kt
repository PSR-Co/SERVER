package com.psr.psr.order.dto

import com.psr.psr.order.entity.Order
import java.time.format.DateTimeFormatter

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
) {
    companion object {
        fun toOrderResDTO(order: Order, isSeller: Boolean): OrderRes {
            return OrderRes(
                isSeller = isSeller,
                status = order.orderStatus.value,
                orderUserId = order.user.id!!,
                orderDate = order.createdAt.format(DateTimeFormatter.ISO_DATE),
                productId = order.product.id!!,
                productName = order.product.name,
                ordererName = order.ordererName,
                websiteUrl = order.websiteUrl,
                inquiry = order.inquiry,
                description = order.description
            )
        }
    }
}