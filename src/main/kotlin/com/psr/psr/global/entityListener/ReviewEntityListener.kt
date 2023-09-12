package com.psr.psr.global.entityListener

import com.psr.psr.order.repository.OrderRepository
import com.psr.psr.review.entity.Review
import com.psr.psr.review.repository.ReviewImgRepository
import com.psr.psr.review.repository.ReviewReportRepository
import jakarta.persistence.PreRemove

class ReviewEntityListener {
    @PreRemove
    fun onUpdate(review: Review) {
        val reviewImgRepository: ReviewImgRepository = BeanUtils.getBean(ReviewImgRepository::class.java)
        reviewImgRepository.deleteByReview(review)
        val reviewReportRepository: ReviewReportRepository = BeanUtils.getBean(ReviewReportRepository::class.java)
        reviewReportRepository.deleteByReview(review)
        val orderRepository: OrderRepository = BeanUtils.getBean(OrderRepository::class.java)
        orderRepository.save(review.order.setReview(null))
    }
}