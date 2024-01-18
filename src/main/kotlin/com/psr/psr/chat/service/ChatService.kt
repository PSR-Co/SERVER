package com.psr.psr.chat.service

import com.psr.psr.chat.dto.request.ChatMessageReq
import com.psr.psr.chat.dto.response.ChatMessageRes
import com.psr.psr.chat.dto.response.GetChatMessagesRes
import com.psr.psr.chat.dto.response.GetChatRoomsRes
import com.psr.psr.chat.entity.ChatMessage
import com.psr.psr.chat.entity.ChatRoom
import com.psr.psr.chat.repository.ChatMessageRepository
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
    private val chatRoomRepository: ChatRoomRepository,
    private val chatMessageRepository: ChatMessageRepository
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
        if (chatRoom.senderUser == null && chatRoom.receiverUser == null)
            chatRoomRepository.delete(chatRoom)
    }

    @Transactional
    fun createChatMessage(user: User, chatRoomId: Long, request: ChatMessageReq) {
        val chatRoom: ChatRoom = chatRoomRepository.findByIdAndStatus(chatRoomId, Constant.UserStatus.ACTIVE_STATUS)
            ?: throw BaseException(BaseResponseCode.NOT_FOUND_CHATROOM)
        chatMessageRepository.save(ChatMessage.toEntity(user, chatRoom, request.message))
    }

    fun getChatRooms(user: User): GetChatRoomsRes? {
        val chatRooms: List<ChatRoom>? = chatRoomRepository.getChatRooms(user)
        if (chatRooms?.isEmpty() == true) return null;
        return chatMessageRepository.getRecentChat(user, chatRooms!!)
    }

    fun getChatMessages(user: User, chatRoomId: Long): GetChatMessagesRes {
        val chatRoom: ChatRoom = chatRoomRepository.findByIdAndStatus(chatRoomId, Constant.UserStatus.ACTIVE_STATUS)
            ?: throw BaseException(BaseResponseCode.NOT_FOUND_CHATROOM)
        val chatMessages :List<ChatMessageRes>? = chatMessageRepository.getChatMessages(user, chatRoom)
        return GetChatMessagesRes.toDto(chatRoom.order.product.name, chatMessages)
    }
}