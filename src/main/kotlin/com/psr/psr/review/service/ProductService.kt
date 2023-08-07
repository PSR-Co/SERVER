package com.psr.psr.review.service

import com.psr.psr.review.repository.ReviewRepository
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val reviewRepository: ReviewRepository
) {

}