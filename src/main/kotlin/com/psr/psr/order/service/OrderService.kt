package com.psr.psr.order.service

import com.psr.psr.global.Constant.OrderType.OrderType.SELL
import com.psr.psr.global.Constant.UserStatus.UserStatus.ACTIVE_STATUS
import com.psr.psr.global.exception.BaseException
import com.psr.psr.global.exception.BaseResponseCode
import com.psr.psr.order.dto.*
import com.psr.psr.order.entity.Order
import com.psr.psr.order.entity.OrderStatus
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
    // 요청하기
    fun makeOrder(user: User, orderReq: OrderReq) {
        val product: Product = orderReq.productId?.let { productRepository.findByIdAndStatus(it, ACTIVE_STATUS) }
            ?: throw BaseException(BaseResponseCode.NOT_FOUND_PRODUCT)
        orderRepository.save(orderAssembler.toEntity(user, orderReq, product))
    }

    // 요청 상세 조회
    fun getOrderDetail(user: User, orderId: Long): OrderRes {
        val order: Order = orderRepository.findByIdAndStatus(orderId, ACTIVE_STATUS)
            ?: throw BaseException(BaseResponseCode.NOT_FOUND_ORDER)
        val isSeller = order.product.user.id == user.id
        if (order.user.id != user.id && !isSeller) throw BaseException(BaseResponseCode.NO_PERMISSION)
        return orderAssembler.toOrderResDTO(order, isSeller)
    }

    // 요청 목록 조회
    fun getOrderList(user: User, type: String, status: String): OrderListRes {
        val orderStatus: OrderStatus = OrderStatus.findByName(status)
        val orders: List<OrderListComp> = (
                if (type == SELL)
                    orderRepository.findByProductUserAndOrderStatusAndStatus(user, orderStatus, ACTIVE_STATUS)
                else
                    orderRepository.findByUserAndOrderStatusAndStatus(user, orderStatus, ACTIVE_STATUS)
                )
            .map { order: Order -> orderAssembler.toPrepareListDto(order, type) }

        return orderAssembler.toListDto(orders)
    }

    // 요청 수정
    fun editOrder(user: User, orderReq: OrderReq, orderId: Long) {
        val order: Order = orderRepository.findByIdAndStatus(orderId, ACTIVE_STATUS)
            ?: throw BaseException(BaseResponseCode.NOT_FOUND_ORDER)
        if (order.user.id != user.id) throw BaseException(BaseResponseCode.NO_PERMISSION)
        order.editOrder(orderReq)
        orderRepository.save(order)
    }
}