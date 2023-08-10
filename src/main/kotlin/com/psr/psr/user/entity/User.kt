package com.psr.psr.user.entity

import com.psr.psr.global.entity.BaseEntity
import com.psr.psr.product.entity.Product
import jakarta.persistence.*
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.Where
import org.jetbrains.annotations.NotNull

@DynamicUpdate
@DynamicInsert
@Entity
class User(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        @NotNull
        @Column(length = 100)
        var email: String,

        @NotNull
        @Column(length = 300)
        var password:String,

        @NotNull
        @Enumerated(EnumType.STRING)
        var type:Type,

        @NotNull
        @Column(length = 30)
        var nickname:String,

        @NotNull
        @Column(length = 30)
        var name:String,

        @NotNull
        @Column(length = 15)
        var phone:String,

        var imgUrl: String? = null,

        @NotNull
        @Enumerated(EnumType.STRING)
        var provider: Provider,

        @NotNull
        var marketing: Boolean,

        @NotNull
        var notification: Boolean,

        @OneToMany(mappedBy = "user")
        @Where(clause = "status = 'active'")
        var products: List<Product>? = null

): BaseEntity()
