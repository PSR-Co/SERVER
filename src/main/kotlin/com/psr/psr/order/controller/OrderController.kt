package com.psr.psr.order.controller

import com.psr.psr.global.dto.BaseResponse
import com.psr.psr.global.jwt.UserAccount
import com.psr.psr.order.dto.OrderReq
import com.psr.psr.order.dto.OrderRes
import com.psr.psr.order.service.OrderService
import jakarta.validation.Valid
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/orders")
class OrderController(
        private val orderService: OrderService
) {
        // 요청하기
        @PostMapping
        fun makeOrder (@AuthenticationPrincipal userAccount: UserAccount, @RequestBody @Valid orderReq: OrderReq) : BaseResponse<Unit> {
                if (orderReq.websiteUrl.isNullOrBlank()) orderReq.websiteUrl = null
                return BaseResponse(orderService.makeOrder(userAccount.getUser(), orderReq))
        }

        // 요청 상세 조회
        @GetMapping("/{orderId}")
        fun getOrderDetail (@AuthenticationPrincipal userAccount: UserAccount, @PathVariable orderId: Long) : BaseResponse<OrderRes> {
                return BaseResponse(orderService.getOrderDetail(userAccount.getUser(), orderId))
        }
}