package com.psr.psr.order.dto

import com.psr.psr.global.Constant.OrderType.OrderType.SELL
import com.psr.psr.order.entity.Order
import com.psr.psr.product.entity.Product
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

    fun toListDto(order: Order, type: String): OrderListRes {
        val userName =
            if (type == SELL) order.ordererName
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