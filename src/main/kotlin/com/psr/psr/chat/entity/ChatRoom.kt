package com.psr.psr.chat.entity

import com.psr.psr.global.entity.BaseEntity
import com.psr.psr.user.entity.User
import jakarta.persistence.*

@Entity
data class ChatRoom(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long,

        @ManyToOne
        @JoinColumn(nullable = false, name = "sender_user_idx")
        var senderUser: User,

        @ManyToOne
        @JoinColumn(nullable = false, name = "receiver_user_idx")
        var receiverUser: User

): BaseEntity()
