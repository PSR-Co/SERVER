package com.psr.psr.order.entity

import com.psr.psr.global.exception.BaseException
import com.psr.psr.global.exception.BaseResponseCode

enum class OrderStatus(val statusName: String) {
    ORDER_WAITING("요청대기"),
    PROGRESSING("진행중"),
    COMPLETED("진행완료"),
    CANCELED("요청취소");

    companion object {
        fun findByName(statusName: String): OrderStatus {
            return OrderStatus.values().find { it.statusName == statusName }
                ?: throw BaseException(BaseResponseCode.INVALID_ORDER_STATUS)
        }
    }
}
