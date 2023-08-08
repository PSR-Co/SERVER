package com.psr.psr.review.repository

import com.psr.psr.review.entity.Review
import com.psr.psr.review.entity.ReviewImg
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewImgRepository: JpaRepository<ReviewImg, Long> {
    fun deleteByReview(review: Review)
}