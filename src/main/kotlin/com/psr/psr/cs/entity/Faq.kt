package com.psr.psr.cs.entity

import com.psr.psr.global.entity.BaseEntity
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.jetbrains.annotations.NotNull

@Entity
@SQLDelete(sql = "UPDATE faq SET status = 'inactive', updated_at = current_timestamp WHERE id = ?")
data class Faq(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long,

        @NotNull
        @Column(length = 100)
        var title: String,

        @NotNull
        var content: String,

        @NotNull
        @Enumerated(EnumType.STRING)
        var type: FaqType

): BaseEntity()
