package com.psr.psr.product.entity

import com.psr.psr.global.entity.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import org.jetbrains.annotations.NotNull

@Entity
data class ProductImg(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long,

    @ManyToOne
        var product: Product,

    @NotNull
        var imgKey: String

): BaseEntity()
