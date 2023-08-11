package com.psr.psr.product.repository

import com.psr.psr.product.entity.Product
import com.psr.psr.product.entity.ProductLike
import com.psr.psr.user.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductLikeRepository: JpaRepository<ProductLike, Long> {
    fun existsByProductAndUserAndStatus(product: Product, user: User, activeStatus: String): Boolean
    fun countByProductAndStatus(product: Product, activeStatus: String): Int
    fun findByUserAndStatus(user: User, activeStatus: String, pageable: Pageable): Page<ProductLike>?
    fun findByProductAndUser(product: Product, user: User): ProductLike?
}