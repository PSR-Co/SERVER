package com.psr.psr.chat.repository

import com.psr.psr.chat.dto.response.ChatMessageRes
import com.psr.psr.chat.dto.response.ChatMessagesRes
import com.psr.psr.chat.dto.response.ChatRoomRes
import com.psr.psr.chat.dto.response.GetChatRoomsRes
import com.psr.psr.chat.entity.ChatMessage
import com.psr.psr.chat.entity.ChatRoom
import com.psr.psr.chat.entity.QChatMessage.chatMessage
import com.psr.psr.global.Constant.UserStatus.UserStatus.ACTIVE_STATUS
import com.psr.psr.user.entity.User
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class ChatMessageRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : ChatMessageCustom {
    override fun getRecentChat(user: User, chatRooms: List<ChatRoom>): GetChatRoomsRes? {

        val response: MutableList<ChatRoomRes> = mutableListOf()

        for (chatRoom in chatRooms) {
            val chat: ChatMessage = queryFactory
                .selectFrom(chatMessage)
                .where(
                    chatMessage.chatRoom.eq(chatRoom),
                    chatMessage.status.eq(ACTIVE_STATUS),
                )
                .orderBy(chatMessage.updatedAt.desc())
                .fetchFirst()

            val numOfUnread: Int = queryFactory
                .select(chatMessage)
                .from(chatMessage)
                .where(
                    chatMessage.chatRoom.eq(chatRoom),
                    chatMessage.status.eq(ACTIVE_STATUS),
                    chatMessage.isRead.eq(false),
                    !chatMessage.senderUser.eq(user)
                )
                .fetch()
                .size

            val partner: User =
                if (user == chatRoom.order.user) chatRoom.order.product.user
                else chatRoom.order.user

            val chatRoomRes: ChatRoomRes = ChatRoomRes.toDto(chatRoom, chat, partner, numOfUnread)
            response.add(chatRoomRes)
        }

        return GetChatRoomsRes.toDto(response)
    }

    override fun getChatMessages(user: User, chatRoom: ChatRoom): List<ChatMessagesRes>? {
        val chatMessages: MutableList<ChatMessage>? = queryFactory
            .selectFrom(chatMessage)
            .where(
                chatMessage.chatRoom.id.eq(chatRoom.id),
                chatMessage.status.eq(ACTIVE_STATUS),
            )
            .orderBy(chatMessage.createdAt.asc())
            .fetch()

        val response: MutableList<ChatMessagesRes> = mutableListOf()
        val messagesOfDate: MutableList<ChatMessageRes> = mutableListOf()
        var standardDate: LocalDate =
            if (chatMessages?.isEmpty() == true) return null
            else chatMessages!![0].createdAt.toLocalDate()

        for (i: Int in 0 until chatMessages.size) {
            if (standardDate == chatMessages[i].createdAt.toLocalDate()) {
                messagesOfDate.add(ChatMessageRes.toDto(user, chatMessages[i]))
            } else {
                response.add(ChatMessagesRes.toDto(standardDate, messagesOfDate.toList()))
                standardDate = chatMessages[i].createdAt.toLocalDate()
                messagesOfDate.clear()
                messagesOfDate.add(ChatMessageRes.toDto(user, chatMessages[i]))
            }
            if (i == chatMessages.size-1) response.add(ChatMessagesRes.toDto(standardDate, messagesOfDate.toList()))
        }
        return response
    }

}