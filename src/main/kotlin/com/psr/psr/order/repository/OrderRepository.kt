package com.psr.psr.order.repository

import com.psr.psr.order.entity.Order
import com.psr.psr.order.entity.OrderStatus
import com.psr.psr.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository: JpaRepository<Order, Long> {
    fun findByIdAndStatus(orderId: Long, status: String): Order?
    fun findByUserAndOrderStatusAndStatus(orderer: User, orderStatus: OrderStatus, status: String): List<Order>
    fun findByProductUserAndOrderStatusAndStatus(seller: User, orderStatus: OrderStatus, status: String): List<Order>
}