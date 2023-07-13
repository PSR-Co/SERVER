package com.psr.psr.cs.entity

import com.psr.psr.global.entity.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Notice(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long
): BaseEntity()
