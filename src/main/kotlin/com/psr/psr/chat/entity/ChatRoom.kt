package com.psr.psr.chat.entity

import com.psr.psr.global.entity.BaseEntity
import com.psr.psr.order.entity.Order
import com.psr.psr.user.entity.User
import jakarta.persistence.*

@Entity
data class ChatRoom(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        @ManyToOne
        @JoinColumn(nullable = true, name = "sender_user_id")
        var senderUser: User?,

        @ManyToOne
        @JoinColumn(nullable = true, name = "receiver_user_id")
        var receiverUser: User?,

        @OneToOne
        @JoinColumn(nullable = false, name = "order_id")
        var order: Order

) : BaseEntity() {
        fun leave(user: User) {
                if (senderUser == user) senderUser = null
                if (receiverUser == user) receiverUser = null
        }

        companion object {
                fun toEntity(user: User, order: Order): ChatRoom {
                        return ChatRoom(
                                senderUser = user,
                                receiverUser = order.user,
                                order = order
                        )
                }
        }
}

