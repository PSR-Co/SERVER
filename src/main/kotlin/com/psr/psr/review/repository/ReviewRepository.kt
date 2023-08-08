package com.psr.psr.review.repository

import com.psr.psr.review.entity.Review
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewRepository: JpaRepository<Review, Long> {
    fun findByIdAndStatus(reviewId: Long, status: String): Review?
}