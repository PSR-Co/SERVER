package com.psr.psr.order.dto

import com.psr.psr.global.Constant
import com.psr.psr.order.entity.Order
import io.swagger.v3.oas.annotations.media.Schema
import java.time.format.DateTimeFormatter

data class OrderListRes (
    @Schema(description = "요청 Id", example = "1")
    val orderId: Long,
    @Schema(description = "요청일자", example = "2023-01-01")
    val orderDate: String,
    @Schema(description = "요청자명 or 판매자명", example = "박서연")
    val userName: String,
    @Schema(description = "상품 Id", example = "1")
    val productId: Long,
    @Schema(description = "상품명", example = "상품명")
    val productName: String,
    @Schema(description = "상품 이미지", example = "http~~")
    val productImgUrl: String?,
    @Schema(description = "리뷰 Id", example = "1")
    val reviewId: Long?
) {
    companion object {
        fun toListDto(order: Order, type: String): OrderListRes {
            val userName =
                if (type == Constant.Order.SELL) order.ordererName
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