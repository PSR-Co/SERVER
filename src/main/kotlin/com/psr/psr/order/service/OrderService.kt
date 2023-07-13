package com.psr.psr.order.service

import com.psr.psr.order.repository.OrderRepository
import org.springframework.stereotype.Service

@Service
class OrderService(
        private val orderRepository: OrderRepository
) {
}