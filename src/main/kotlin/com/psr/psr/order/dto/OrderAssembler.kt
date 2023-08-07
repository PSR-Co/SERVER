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

    fun toPrepareListDto(order: Order, type: String): OrderListReq {
        val userName: String =
            if (type == SELL) order.ordererName
            else order.product.user.nickname
        return OrderListReq(
            orderId = order.id!!,
            orderDate = order.createdAt.format(DateTimeFormatter.ISO_DATE),
            userName = userName,
            productId = order.product.id,
            productName = order.product.name,
            isReviewed = order.isReviewed
        )
    }
}