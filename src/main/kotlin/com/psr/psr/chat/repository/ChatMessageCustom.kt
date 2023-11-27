package com.psr.psr.chat.repository

import com.psr.psr.chat.dto.response.GetChatRoomsRes
import com.psr.psr.chat.entity.ChatRoom
import com.psr.psr.user.entity.User


interface ChatMessageCustom {
    fun getRecentChat(user: User, chatRooms: List<ChatRoom>): GetChatRoomsRes?

}