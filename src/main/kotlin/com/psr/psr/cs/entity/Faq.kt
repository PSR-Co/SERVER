package com.psr.psr.cs.entity

import com.psr.psr.global.entity.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.jetbrains.annotations.NotNull

@Entity
data class Faq(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long,

        @NotNull
        var title: String,

        @NotNull
        var content: String,

        @NotNull
        var type: FaqType

): BaseEntity()
