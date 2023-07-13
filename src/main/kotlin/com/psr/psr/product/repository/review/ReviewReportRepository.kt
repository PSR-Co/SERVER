package com.psr.psr.product.repository.review

import com.psr.psr.product.entity.review.ReviewReport
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewReportRepository: JpaRepository<ReviewReport, Long> {
}