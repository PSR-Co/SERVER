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
class ChatService(
    private val orderRepository: OrderRepository,
    private val chatRoomRepository: ChatRoomRepository
) {
    @Transactional
    fun createChatRoom(user: User, orderId: Long) {
        val order: Order = orderRepository.findByIdAndStatus(orderId, Constant.UserStatus.ACTIVE_STATUS)
            ?: throw BaseException(BaseResponseCode.NOT_FOUND_ORDER)
        chatRoomRepository.save(ChatRoom.toEntity(user, order))
    }

    @Transactional
    fun leaveChatRoom(user: User, chatRoomId: Long) {
        val chatRoom: ChatRoom = chatRoomRepository.findByIdAndStatus(chatRoomId, Constant.UserStatus.ACTIVE_STATUS)
            ?: throw BaseException(BaseResponseCode.NOT_FOUND_CHATROOM)
        chatRoom.leave(user)
        checkChatRoom(chatRoom)
    }

    private fun checkChatRoom(chatRoom: ChatRoom) {
        if(chatRoom.senderUser==null && chatRoom.receiverUser==null)
            chatRoomRepository.delete(chatRoom)
    }
}