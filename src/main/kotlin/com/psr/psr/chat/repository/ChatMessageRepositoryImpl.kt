package com.psr.psr.chat.repository

import com.psr.psr.chat.dto.response.ChatRoomRes
import com.psr.psr.chat.dto.response.GetChatRoomsRes
import com.psr.psr.chat.entity.ChatMessage
import com.psr.psr.chat.entity.ChatRoom
import com.psr.psr.chat.entity.QChatMessage.chatMessage
import com.psr.psr.global.Constant.UserStatus.UserStatus.ACTIVE_STATUS
import com.psr.psr.user.entity.User
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component

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

}