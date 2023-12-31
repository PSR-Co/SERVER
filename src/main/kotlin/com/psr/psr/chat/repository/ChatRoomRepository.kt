package com.psr.psr.chat.repository

import com.psr.psr.chat.entity.ChatRoom
import com.psr.psr.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ChatRoomRepository : JpaRepository<ChatRoom, Long>, ChatRoomCustom {
    fun deleteBySenderUser(user: User)
    fun deleteByReceiverUser(user: User)
    fun findByIdAndStatus(chatRoomId: Long, activeStatus: String): ChatRoom?

}