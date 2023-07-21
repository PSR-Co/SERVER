package com.psr.psr.order.entity

enum class OrderStatus(val value: String) {
    ORDER_WAITING("요청대기"),
    PROGRESSING("진행중"),
    COMPLETED("진행완료"),
    CANCELED("요청취소")
}
