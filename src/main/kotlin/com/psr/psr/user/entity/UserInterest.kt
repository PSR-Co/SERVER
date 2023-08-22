package com.psr.psr.user.entity

import com.psr.psr.global.entity.BaseEntity
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.jetbrains.annotations.NotNull

@Entity
@SQLDelete(sql = "UPDATE user_interest SET status = 'inactive', updated_at = current_timestamp WHERE id = ?")
data class UserInterest(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        @ManyToOne
        @JoinColumn(nullable = false, name = "user_id")
        var user: User,

        @NotNull
        @Enumerated(EnumType.STRING)
        var category: Category

): BaseEntity()
