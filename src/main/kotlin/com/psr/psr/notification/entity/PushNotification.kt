package com.psr.psr.notification.entity

import com.psr.psr.global.entity.BaseEntity
import com.psr.psr.user.entity.User
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Entity
data class PushNotification(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    var user: User,

    @NotNull
    @Column(length = 100)
    var title: String,

    @NotNull
    var content: String,

    // 알림의 주체인 요청, 채팅 등의 ID
    var relatedId: Long,

    @NotNull
    var type: NotificationType

) : BaseEntity()
