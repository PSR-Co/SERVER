package com.psr.psr.product.entity

import com.psr.psr.global.entity.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.SQLDelete
import org.jetbrains.annotations.NotNull

@Entity
@SQLDelete(sql = "UPDATE product_img SET status = 'inactive', updated_at = CURRENT_TIMESTAMP WHERE id = ?")
data class ProductImg(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    var product: Product,

    @NotNull
    var imgUrl: String

) : BaseEntity()
