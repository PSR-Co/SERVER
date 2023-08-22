package com.psr.psr.product.repository


import com.psr.psr.product.entity.Product
import com.psr.psr.user.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository: JpaRepository<Product, Long>, ProductCustom {
    fun findAllByUserAndStatusOrderByCreatedAtDesc(user: User, activeStatus: String, pageable: Pageable): Page<Product>?
    fun findByIdAndStatus(productId: Long, activeStatus: String): Product?
    fun findAllByStatus(activeStatus: String): List<Product>?
    fun deleteByUser(user: User)
}