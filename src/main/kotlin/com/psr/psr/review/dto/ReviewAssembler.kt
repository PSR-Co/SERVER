package com.psr.psr.review.dto

import com.psr.psr.order.entity.Order
import com.psr.psr.product.entity.Product
import com.psr.psr.review.entity.Review
import com.psr.psr.review.entity.ReviewImg
import org.springframework.stereotype.Component

@Component
class ReviewAssembler {
    fun toEntity(order: Order, product: Product, reviewReq: ReviewReq): Review {
        return Review(
            order = order,
            product = product,
            rating = reviewReq.rating,
            content = reviewReq.content
        )
    }

    fun toImgEntity(review: Review, imgKey: String): ReviewImg{
        return ReviewImg(
            review = review,
            imgKey = imgKey
        )
    }

}