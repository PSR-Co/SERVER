package com.psr.psr.order.dto

import com.psr.psr.global.Constant
import com.psr.psr.order.entity.Order
import java.time.format.DateTimeFormatter

data class OrderListRes (
    val orderId: Long,
    val orderDate: String,
    val userName: String,
    val productId: Long,
    val productName: String,
    val productImgUrl: String?,
    val reviewId: Long?
) {
    companion object {
        fun toListDto(order: Order, type: String): OrderListRes {
            val userName =
                if (type == Constant.OrderType.SELL) order.ordererName
                else order.product.user.nickname

            return OrderListRes(
                orderId = order.id!!,
                orderDate = order.createdAt.format(DateTimeFormatter.ISO_DATE),
                userName = userName,
                productId = order.product.id!!,
                productName = order.product.name,
                productImgUrl = order.product.imgs.firstOrNull()?.imgUrl,
                reviewId = order.review?.id
            )
        }
    }
}