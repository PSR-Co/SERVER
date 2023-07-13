package com.psr.psr.order.controller

import com.psr.psr.order.service.OrderService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/order")
class OrderController(
        private val orderService: OrderService
) {
}