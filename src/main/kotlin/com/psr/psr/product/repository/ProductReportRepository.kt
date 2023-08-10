package com.psr.psr.product.repository

import com.psr.psr.product.entity.Product
import com.psr.psr.product.entity.ProductReport
import com.psr.psr.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductReportRepository: JpaRepository<ProductReport, Long> {
    fun findByProductAndUserAndStatus(product: Product, user: User, activeStatus: String): ProductReport?
}