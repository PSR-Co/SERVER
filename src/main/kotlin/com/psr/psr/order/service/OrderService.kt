package com.psr.psr.order.service

import com.psr.psr.global.Constant.OrderType.OrderType.SELL
import com.psr.psr.global.Constant.UserStatus.UserStatus.ACTIVE_STATUS
import com.psr.psr.global.exception.BaseException
import com.psr.psr.global.exception.BaseResponseCode
import com.psr.psr.notification.service.NotificationService
import com.psr.psr.order.dto.OrderListRes
import com.psr.psr.order.dto.OrderReq
import com.psr.psr.order.dto.OrderRes
import com.psr.psr.order.entity.Order
import com.psr.psr.order.entity.OrderStatus
import com.psr.psr.order.repository.OrderRepository
import com.psr.psr.product.entity.Product
import com.psr.psr.product.repository.ProductRepository
import com.psr.psr.user.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val productRepository: ProductRepository,
    private val notificationService: NotificationService
) {
    // 요청하기
    fun makeOrder(user: User, orderReq: OrderReq) {
        val product: Product = orderReq.productId?.let { productRepository.findByIdAndStatus(it, ACTIVE_STATUS) }
            ?: throw BaseException(BaseResponseCode.NOT_FOUND_PRODUCT)
        val order = orderRepository.save(Order.toEntity(user, orderReq, product))
        notificationService.sendNewOrderNoti(order.product.name, order.product.user, order.ordererName, order.id!!)
    }

    // 요청 상세 조회
    fun getOrderDetail(user: User, orderId: Long): OrderRes {
        val order: Order = orderRepository.findByIdAndStatus(orderId, ACTIVE_STATUS)
            ?: throw BaseException(BaseResponseCode.NOT_FOUND_ORDER)
        // 요청자 or 판매자 아니면 예외처리
        val isSeller = order.product.user.id == user.id
        if (order.user.id != user.id && !isSeller) throw BaseException(BaseResponseCode.NO_PERMISSION)

        return OrderRes.toOrderResDTO(order, isSeller)
    }

    // 요청 목록 조회(전체 상태)
    fun getOrderList(user: User, type: String, pageable: Pageable): Page<OrderListRes> {
        val orderList: Page<Order> =
            // 요청 받은 목록
            if (type == SELL)
                orderRepository.findByProductUserAndStatus(user, ACTIVE_STATUS, pageable)
            // 요청한 목록
            else
                orderRepository.findByUserAndStatus(user, ACTIVE_STATUS, pageable)
        return orderList.map { order: Order -> OrderListRes.toListDto(order, type) }
    }

    // 요청 목록 조회(요청 상태별)
    fun getOrderListByOrderStatus(user: User, type: String, status: String, pageable: Pageable): Page<OrderListRes> {
        val orderStatus = OrderStatus.findByValue(status)
        val orderList: Page<Order> =
            // 요청 받은 목록
            if (type == SELL)
                orderRepository.findByProductUserAndOrderStatusAndStatus(user, orderStatus, ACTIVE_STATUS, pageable)
            // 요청한 목록
            else
                orderRepository.findByUserAndOrderStatusAndStatus(user, orderStatus, ACTIVE_STATUS, pageable)
        return orderList.map { order: Order -> OrderListRes.toListDto(order, type) }
    }

    // 요청 수정
    fun editOrder(user: User, orderReq: OrderReq?, status: String?, orderId: Long) {
        val order: Order = orderRepository.findByIdAndStatus(orderId, ACTIVE_STATUS)
            ?: throw BaseException(BaseResponseCode.NOT_FOUND_ORDER)
        if (order.user.id != user.id) throw BaseException(BaseResponseCode.NO_PERMISSION)

        var orderStatus: OrderStatus? = null
        if (status != null) orderStatus = OrderStatus.findByValue(status)

        order.editOrder(orderReq, orderStatus)
        val saveOrder = orderRepository.save(order)

        // 요청 상태 변경 시 알림 전송
        if (status != null)
            notificationService.sendChangeOrderStatusNoti(order.product.name, order.product.user, saveOrder.orderStatus, order.id!!)
    }

    // 2달 뒤 요청상태 입력 요망 알림(오후 1시마다 실행)
    @Scheduled(cron = "0 0 13 * * ?", zone = "Asia/Seoul")
    fun notify2MonthOrders() {
        // 진행 중인 요청
        orderRepository.find2MonthAgoOrders(OrderStatus.PROGRESSING)
            .forEach {
                notificationService.send2MonthOrderNoti(
                    it.product.name,
                    it.product.user,
                    it.ordererName,
                    it.id!!
                )
            }

        // 대기중인 요청
        orderRepository.find2MonthAgoOrders(OrderStatus.ORDER_WAITING)
            .forEach {
                notificationService.send2MonthOrderNoti(
                    it.product.name,
                    it.product.user,
                    it.ordererName,
                    it.id!!
                )
            }
    }
}