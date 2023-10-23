package com.psr.psr.order.controller

import com.psr.psr.global.Constant
import com.psr.psr.global.Constant.Order.Order.ORDER
import com.psr.psr.global.Constant.Order.Order.SELL
import com.psr.psr.global.Constant.Order.Order.STATUS
import com.psr.psr.global.dto.BaseResponse
import com.psr.psr.global.exception.BaseResponseCode
import com.psr.psr.global.jwt.UserAccount
import com.psr.psr.order.dto.OrderListRes
import com.psr.psr.order.dto.OrderReq
import com.psr.psr.order.dto.OrderRes
import com.psr.psr.order.service.OrderService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/orders")
@Tag(name = "Order", description = "요청 API")
@SecurityRequirement(name = "Bearer")
class OrderController(
    private val orderService: OrderService
) {
    // 요청하기
    @PostMapping
    fun makeOrder(
        @AuthenticationPrincipal userAccount: UserAccount,
        @RequestBody @Valid orderReq: OrderReq
    ): BaseResponse<Unit> {
        if (orderReq.productId == null) return BaseResponse(BaseResponseCode.NULL_PRODUCT_ID)
        // 선택사항 미입력 시 null 처리
        if (orderReq.websiteUrl.isNullOrBlank()) orderReq.websiteUrl = null
        return BaseResponse(orderService.makeOrder(userAccount.getUser(), orderReq))
    }

    // 요청 상세 조회
    @GetMapping("/{orderId}")
    fun getOrderDetail(
        @AuthenticationPrincipal userAccount: UserAccount,
        @PathVariable orderId: Long
    ): BaseResponse<OrderRes> {
        return BaseResponse(orderService.getOrderDetail(userAccount.getUser(), orderId))
    }

    // 요청 목록 조회
    @GetMapping
    fun getOrderList(
        @AuthenticationPrincipal userAccount: UserAccount,
        @RequestParam type: String,
        @RequestParam(required = false) status: String?,
        @PageableDefault(size = 10, sort = ["id"], direction = Sort.Direction.DESC) pageable: Pageable
    ): BaseResponse<Page<OrderListRes>> {
        if (type !in listOf(SELL, ORDER)) return BaseResponse(BaseResponseCode.INVALID_ORDER_TYPE)

        // 전체 요청 상태 조회
        return if (status == null) BaseResponse(orderService.getOrderList(userAccount.getUser(), type, pageable))
        // 요청 상태별 조회
        else BaseResponse(orderService.getOrderListByOrderStatus(userAccount.getUser(), type, status, pageable))
    }

    // 요청 수정
    @PatchMapping("/{orderId}")
    fun editOrder(
        @AuthenticationPrincipal userAccount: UserAccount,
        @PathVariable orderId: Long,
        @RequestBody @Valid orderReq: OrderReq
    ): BaseResponse<Unit> {
        if (orderReq.websiteUrl.isNullOrBlank()) orderReq.websiteUrl = null
        return BaseResponse(orderService.editOrder(userAccount.getUser(), orderReq, orderId))
    }

    // 요청 상태 수정
    @PatchMapping("/{orderId}/status")
    fun editOrderStatus(
        @AuthenticationPrincipal userAccount: UserAccount,
        @PathVariable orderId: Long,
        @RequestBody status: Map<String, String>
    ): BaseResponse<Unit> {
        status[Constant.Order.STATUS] ?: return BaseResponse(BaseResponseCode.NULL_ORDER_STATUS)
        return BaseResponse(orderService.editOrderStatus(userAccount.getUser(), status[STATUS]!!, orderId))
    }
}