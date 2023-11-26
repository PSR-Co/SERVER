package com.psr.psr.chat.service

import com.psr.psr.chat.entity.ChatRoom
import com.psr.psr.chat.repository.ChatRoomRepository
import com.psr.psr.global.Constant
import com.psr.psr.global.exception.BaseException
import com.psr.psr.global.exception.BaseResponseCode
import com.psr.psr.order.entity.Order
import com.psr.psr.order.repository.OrderRepository
import com.psr.psr.user.entity.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChatRoomService(
    private val orderRepository: OrderRepository,
    private val chatRoomRepository: ChatRoomRepository
) {
    @Transactional
    fun createChatRoom(user: User, orderId: Long) {
        val order: Order = orderRepository.findByIdAndStatus(orderId, Constant.UserStatus.ACTIVE_STATUS)
            ?: throw BaseException(BaseResponseCode.NOT_FOUND_ORDER)
        chatRoomRepository.save(ChatRoom.toEntity(user, order));
    }
}