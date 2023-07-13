package com.psr.psr.product.repository.product

import com.psr.psr.product.entity.product.ProductReport
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductReportRepository: JpaRepository<ProductReport, Long> {
}