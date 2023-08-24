package com.psr.psr.order.entity

import com.psr.psr.global.exception.BaseException
import com.psr.psr.global.exception.BaseResponseCode
import com.psr.psr.global.resolver.EnumType

enum class OrderStatus(override val value: String, val notiSentence: String): EnumType {
    ORDER_WAITING("요청대기", "님의 요청을 확인해주세요!"),
    PROGRESSING("진행중", "요청이 진행되었습니다"),
    COMPLETED("진행완료", "요청이 진행 완료되었습니다"),
    CANCELED("요청취소", "요청이 취소되었습니다");

    companion object {
        fun findByValue(value: String): OrderStatus {
            return enumValues<OrderStatus>().find { it.value == value }
                ?: throw BaseException(BaseResponseCode.INVALID_ORDER_STATUS)
        }
    }
}
