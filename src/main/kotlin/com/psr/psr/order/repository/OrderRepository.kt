package com.psr.psr.order.repository

import com.psr.psr.order.entity.Order
import com.psr.psr.order.entity.OrderStatus
import com.psr.psr.user.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface OrderRepository: JpaRepository<Order, Long> {
    fun findByIdAndStatus(orderId: Long, status: String): Order?
    fun findByUserAndOrderStatusAndStatus(orderer: User, orderStatus: OrderStatus, status: String, pageable: Pageable): Page<Order>
    fun findByProductUserAndOrderStatusAndStatus(seller: User, orderStatus: OrderStatus, status: String, pageable: Pageable): Page<Order>
    fun findByUserAndStatus(orderer: User, status: String, pageable: Pageable): Page<Order>
    fun findByProductUserAndStatus(seller: User, status: String, pageable: Pageable): Page<Order>
    fun deleteByUser(user: User)
    fun findByCreatedAt_DateAndOrderStatusAndStatus(date: LocalDate, orderStatus: OrderStatus, status: String): List<Order>
}