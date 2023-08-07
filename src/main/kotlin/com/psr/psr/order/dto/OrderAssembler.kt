package com.psr.psr.order.dto

import com.psr.psr.global.Constant.OrderType.OrderType.SELL
import com.psr.psr.order.entity.Order
import com.psr.psr.product.entity.product.Product
import com.psr.psr.user.entity.User
import org.springframework.stereotype.Component
import java.time.format.DateTimeFormatter

@Component
class OrderAssembler {
    fun toEntity(user: User, orderReq: OrderReq, product: Product): Order {
        return Order(
            user = user,
            product = product,
            ordererName = orderReq.ordererName,
            websiteUrl = orderReq.websiteUrl,
            inquiry = orderReq.inquiry,
            description = orderReq.description
        )
    }

    fun toOrderResDTO(order: Order, isSeller: Boolean): OrderRes {
        return OrderRes(
            isSeller = isSeller,
            status = order.orderStatus.statusName,
            orderUserId = order.user.id!!,
            orderDate = order.createdAt.format(DateTimeFormatter.ISO_DATE),
            productId = order.product.id,
            productName = order.product.name,
            ordererName = order.ordererName,
            websiteUrl = order.websiteUrl,
            inquiry = order.inquiry,
            description = order.description
        )
    }

    // 요청 목록 조회 시
    fun toListDto(order: Order, type: String, productImgKey: String): OrderListRes {
        val userName =
            if (type == SELL) order.ordererName
            else order.product.user.nickname
        val profileImg: String? =
            if (type == SELL) order.user.imgKey
            else order.product.user.imgKey

        return OrderListRes(
            orderId = order.id!!,
            orderDate = order.createdAt.format(DateTimeFormatter.ISO_DATE),
            userName = userName,
            profileImgKey = profileImg,
            productId = order.product.id,
            productName = order.product.name,
            productImgKey = productImgKey,
            isReviewed = null
        )
    }

    // 마이페이지 요청 목록 조회 시
    fun toListDto(order: Order, type: String): OrderListRes {
        val userName =
            if (type == SELL) order.ordererName
            else order.product.user.nickname

        return OrderListRes(
            orderId = order.id!!,
            orderDate = order.createdAt.format(DateTimeFormatter.ISO_DATE),
            userName = userName,
            profileImgKey = null,
            productId = order.product.id,
            productName = order.product.name,
            productImgKey = null,
            isReviewed = order.isReviewed
        )
    }
}