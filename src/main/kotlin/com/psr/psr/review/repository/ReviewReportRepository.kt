package com.psr.psr.review.repository

import com.psr.psr.review.entity.ReviewReport
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewReportRepository: JpaRepository<ReviewReport, Long> {
}