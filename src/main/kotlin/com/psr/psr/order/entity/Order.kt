package com.psr.psr.order.entity

import com.psr.psr.global.entity.BaseEntity
import com.psr.psr.order.dto.OrderReq
import com.psr.psr.product.entity.product.Product
import com.psr.psr.user.entity.User
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Entity
@Table(name = "orders")
data class Order(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(nullable = false, name = "product_id")
    var product: Product,

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    var user: User,

    @NotNull
    @Column(length = 100)
    var ordererName: String,

    var websiteUrl: String?,

    @NotNull
    @Enumerated(EnumType.STRING)
    var orderStatus: OrderStatus = OrderStatus.ORDER_WAITING,

    @NotNull
    var inquiry: String,

    @NotNull
    var description: String,

    @NotNull
    var isReviewed: Boolean = false

) : BaseEntity() {
    fun editOrder(orderReq: OrderReq) {
        this.ordererName = orderReq.ordererName
        this.websiteUrl = orderReq.websiteUrl
        this.inquiry = orderReq.inquiry
        this.description = orderReq.description
    }
}
