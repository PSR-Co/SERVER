package com.psr.psr.notification.entity

import com.psr.psr.global.entity.BaseEntity
import com.psr.psr.user.entity.User
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Entity
data class Notification(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    var user: User,

    @NotNull
    @Column(length = 100)
    var title: String,

    @NotNull
    var content: String

) : BaseEntity()
