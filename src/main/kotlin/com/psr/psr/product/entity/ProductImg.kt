package com.psr.psr.product.entity

import com.psr.psr.global.entity.BaseEntity
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Entity
data class ProductImg(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    var product: Product,

    @NotNull
    var imgUrl: String

) : BaseEntity() {
    companion object {
        fun toEntity(product: Product, imgUrl: String): ProductImg {
            return ProductImg(
                product = product,
                imgUrl = imgUrl
            )
        }
    }
}
