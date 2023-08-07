package com.psr.psr.review.repository

import com.psr.psr.review.entity.Review
import com.psr.psr.review.entity.ReviewReport
import com.psr.psr.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewReportRepository: JpaRepository<ReviewReport, Long> {
    fun findByReviewAndUserAndStatus(review: Review, user: User, status: String): ReviewReport?
}