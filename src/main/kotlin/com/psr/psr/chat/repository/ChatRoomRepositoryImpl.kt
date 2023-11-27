package com.psr.psr.chat.repository

import com.psr.psr.chat.entity.ChatRoom
import com.psr.psr.chat.entity.QChatRoom.chatRoom
import com.psr.psr.global.Constant.UserStatus.UserStatus.ACTIVE_STATUS
import com.psr.psr.user.entity.User
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component

@Component
class ChatRoomRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : ChatRoomCustom {
    override fun getChatRooms(user: User): List<ChatRoom> {
        return queryFactory
            .selectFrom(chatRoom)
            .where(
                chatRoom.status.eq(ACTIVE_STATUS),
                (chatRoom.senderUser.eq(user)).or(chatRoom.receiverUser.eq(user))
            )
            .orderBy(chatRoom.updatedAt.desc())
            .fetch()
    }

}