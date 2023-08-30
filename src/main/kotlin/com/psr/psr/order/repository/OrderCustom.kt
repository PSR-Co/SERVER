package com.psr.psr.order.repository

import com.psr.psr.order.entity.Order
import com.psr.psr.order.entity.OrderStatus

interface OrderCustom {
    fun find2MonthAgoOrders(orderStatus: OrderStatus): List<Order>
}