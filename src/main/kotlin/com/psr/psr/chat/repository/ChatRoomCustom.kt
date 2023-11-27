package com.psr.psr.chat.repository

import com.psr.psr.chat.entity.ChatRoom
import com.psr.psr.user.entity.User


interface ChatRoomCustom {
    fun getChatRooms(user: User): List<ChatRoom>?

}