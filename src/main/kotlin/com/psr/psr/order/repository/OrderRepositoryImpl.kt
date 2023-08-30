package com.psr.psr.order.repository

import com.psr.psr.global.Constant.UserStatus.UserStatus.ACTIVE_STATUS
import com.psr.psr.order.entity.Order
import com.psr.psr.order.entity.OrderStatus
import com.psr.psr.order.entity.QOrder.order
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component

import java.time.LocalDate

@Component
class OrderRepositoryImpl(
    private val queryFactory: JPAQueryFactory
): OrderCustom {
    override fun find2MonthAgoOrders(orderStatus: OrderStatus): List<Order> {
        val dateTemplate = Expressions.dateTemplate(LocalDate::class.java, "date({0})", order.createdAt)
        val date = LocalDate.now().minusMonths(2)
        return queryFactory
            .selectFrom(order)
            .where(dateTemplate.eq(date)
                .and(order.orderStatus.eq(orderStatus))
                .and(order.status.eq(ACTIVE_STATUS)))
            .fetch()
    }

}