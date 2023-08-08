package com.psr.psr.review.repository

import com.psr.psr.product.entity.Product
import com.psr.psr.review.entity.Review
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewRepository: JpaRepository<Review, Long> {
    fun findByIdAndStatus(reviewId: Long, status: String): Review?
    fun findByProductAndStatus(product: Product, status: String, pageable: Pageable): Page<Review>
}