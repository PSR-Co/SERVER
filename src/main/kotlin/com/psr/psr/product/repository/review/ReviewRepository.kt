package com.psr.psr.product.repository.review

import com.psr.psr.product.entity.review.Review
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewRepository: JpaRepository<Review, Long> {
}