package com.psr.psr.product.repository.product


import com.psr.psr.product.entity.product.Product
import com.psr.psr.user.entity.User

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository: JpaRepository<Product, Long>, ProductCustom {
    fun findAllByUserAndStatus(user: User, activeStatus: String): List<Product>?


}