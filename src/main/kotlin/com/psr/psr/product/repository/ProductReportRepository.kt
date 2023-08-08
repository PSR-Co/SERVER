package com.psr.psr.product.repository

import com.psr.psr.product.entity.ProductReport
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductReportRepository: JpaRepository<ProductReport, Long> {
}