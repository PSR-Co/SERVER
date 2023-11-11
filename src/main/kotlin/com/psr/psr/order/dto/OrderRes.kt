package com.psr.psr.order.dto

import com.psr.psr.order.entity.Order
import io.swagger.v3.oas.annotations.media.Schema
import java.time.format.DateTimeFormatter

data class OrderRes (
    @Schema(description = "판매자 여부", example = "true")
    val isSeller: Boolean,
    @Schema(description = "요청 상태", example = "요청대기")
    val status: String,
    @Schema(description = "요청자 Id", example = "1")
    val orderUserId: Long,
    @Schema(description = "요청일자", example = "2023-01-01")
    val orderDate: String,
    @Schema(description = "상품 Id", example = "1")
    val productId: Long,
    @Schema(description = "상품명", example = "상품명")
    val productName: String,
    @Schema(description = "요청자 이름", example = "박서연")
    val ordererName: String,
    @Schema(description = "사업자/쇼핑몰 URL", example = "www.123.com")
    var websiteUrl: String? = null,
    @Schema(description = "문의사항", example = "문의사항 내용")
    val inquiry: String,
    @Schema(description = "요청 상세 설명", example = "요청 상세 설명")
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