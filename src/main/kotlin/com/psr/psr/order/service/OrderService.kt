package com.psr.psr.order.service

import com.psr.psr.global.Constant.USER_STATUS.USER_STATUS.ACTIVE_STATUS
import com.psr.psr.global.exception.BaseException
import com.psr.psr.global.exception.BaseResponseCode
import com.psr.psr.order.dto.OrderAssembler
import com.psr.psr.order.dto.OrderReq
import com.psr.psr.order.repository.OrderRepository
import com.psr.psr.product.entity.product.Product
import com.psr.psr.product.repository.product.ProductRepository
import com.psr.psr.user.entity.User
import org.springframework.stereotype.Service

@Service
class OrderService(
        private val orderRepository: OrderRepository,
        private val productRepository: ProductRepository,
        private val orderAssembler: OrderAssembler
) {
        fun makeOrder(user: User, orderReq: OrderReq) {
                val product: Product = productRepository.findByIdAndStatus(orderReq.productId, ACTIVE_STATUS)
                        ?: throw BaseException(BaseResponseCode.NOT_FOUND_PRODUCT)
                orderRepository.save(orderAssembler.toEntity(user, orderReq, product))
        }
}