package com.psr.psr.chat.entity

import com.psr.psr.global.entity.BaseEntity
import com.psr.psr.user.entity.User
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Entity
data class ChatMessage(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(nullable = false, name = "sender_user_id")
    var senderUser: User,

    @ManyToOne
    @JoinColumn(nullable = false, name = "chat_room_id")
    var chatRoom: ChatRoom,

    @NotNull
    var message: String

) : BaseEntity() {
    companion object {
        fun toEntity(user: User, chatRoom: ChatRoom, message: String): ChatMessage {
            return ChatMessage(
                senderUser = user,
                chatRoom = chatRoom,
                message = message
            )
        }
    }
}
