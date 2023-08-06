package com.psr.psr.product.repository.product


import com.psr.psr.product.entity.product.Product
import com.psr.psr.user.entity.User

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository: JpaRepository<Product, Long>, ProductCustom {
    fun findAllByUserAndStatusOrderByCreatedAtDesc(user: User, activeStatus: String): List<Product>?
    fun findByIdAndStatus(productId: Long, activeStatus: String): Product?
}