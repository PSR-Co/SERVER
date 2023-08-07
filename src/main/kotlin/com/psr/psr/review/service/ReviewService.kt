package com.psr.psr.review.service

import com.psr.psr.global.Constant.UserStatus.UserStatus.ACTIVE_STATUS
import com.psr.psr.global.exception.BaseException
import com.psr.psr.global.exception.BaseResponseCode
import com.psr.psr.order.repository.OrderRepository
import com.psr.psr.product.entity.Product
import com.psr.psr.product.repository.ProductRepository
import com.psr.psr.review.dto.ReviewAssembler
import com.psr.psr.review.dto.ReviewListRes
import com.psr.psr.review.dto.ReviewReq
import com.psr.psr.review.entity.Review
import com.psr.psr.review.repository.ReviewImgRepository
import com.psr.psr.review.repository.ReviewRepository
import com.psr.psr.user.entity.User
import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ReviewService(
    private val reviewRepository: ReviewRepository,
    private val orderRepository: OrderRepository,
    private val productRepository: ProductRepository,
    private val reviewImgRepository: ReviewImgRepository,
    private val reviewAssembler: ReviewAssembler
) {
    // 리뷰 생성
    @Transactional
    fun makeReview(user: User, orderId: Long, reviewReq: ReviewReq) {
        val order = orderRepository.findByIdAndStatus(orderId, ACTIVE_STATUS)
            ?: throw BaseException(BaseResponseCode.NOT_FOUND_ORDER)

        if (order.user.id != user.id) throw BaseException(BaseResponseCode.NO_PERMISSION)
        if (order.isReviewed) throw BaseException(BaseResponseCode.REVIEW_ALREADY_COMPLETE)

        val product: Product = productRepository.findByIdAndStatus(order.product.id, ACTIVE_STATUS)
            ?: throw BaseException(BaseResponseCode.NOT_FOUND_PRODUCT)

        val review = reviewRepository.save(reviewAssembler.toEntity(order, product, reviewReq))
        orderRepository.save(order.changeReviewStatus())

        if (reviewReq.imgList != null) {
            val reviewImgs = reviewReq.imgList.map { img: String -> reviewAssembler.toImgEntity(review, img) }
            reviewImgRepository.saveAll(reviewImgs)
        }
    }

    // 리뷰 삭제
    @Transactional
    fun deleteReview(user: User, reviewId: Long) {
        val review: Review = reviewRepository.findByIdAndStatus(reviewId, ACTIVE_STATUS)
            ?: throw BaseException(BaseResponseCode.NOT_FOUND_REVIEW)
        if (review.order.user.id != user.id) throw BaseException(BaseResponseCode.NO_PERMISSION)

        reviewImgRepository.deleteByReviewAndStatus(review, ACTIVE_STATUS)
        reviewRepository.delete(review)
        orderRepository.save(review.order.changeReviewStatus())
    }

    // 상품별 리뷰 목록 조회
    fun getProductReviews(productId: Long, pageable: Pageable): Page<ReviewListRes> {
        val product: Product = productRepository.findByIdAndStatus(productId, ACTIVE_STATUS)
            ?: throw BaseException(BaseResponseCode.NOT_FOUND_PRODUCT)

        return reviewRepository.findByProductAndStatus(product, ACTIVE_STATUS, pageable)
            .map { review -> reviewAssembler.toDto(review) }
    }
}