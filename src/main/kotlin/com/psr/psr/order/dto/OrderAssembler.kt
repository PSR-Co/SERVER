package com.psr.psr.order.dto

import com.psr.psr.order.entity.Order
import com.psr.psr.product.entity.product.Product
import com.psr.psr.user.entity.User
import org.springframework.stereotype.Component

@Component
class OrderAssembler {
    fun toEntity(user: User, orderReq: OrderReq, product: Product): Order {
        return Order(
            user = user,
            product = product,
            ordererName = orderReq.ordererName!!,
            websiteUrl = orderReq.websiteUrl,
            inquiry = orderReq.inquiry!!,
            description = orderReq.description!!
        )
    }
}