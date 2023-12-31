package com.psr.psr.review.service

import com.psr.psr.global.Constant.UserStatus.UserStatus.ACTIVE_STATUS
import com.psr.psr.global.entity.ReportCategory
import com.psr.psr.global.exception.BaseException
import com.psr.psr.global.exception.BaseResponseCode
import com.psr.psr.order.entity.OrderStatus
import com.psr.psr.order.repository.OrderRepository
import com.psr.psr.product.entity.Product
import com.psr.psr.product.repository.ProductRepository
import com.psr.psr.review.dto.GetProductDetailRes
import com.psr.psr.review.dto.ReviewListRes
import com.psr.psr.review.dto.ReviewReq
import com.psr.psr.review.dto.ReviewRes
import com.psr.psr.review.entity.Review
import com.psr.psr.review.entity.ReviewImg
import com.psr.psr.review.entity.ReviewReport
import com.psr.psr.review.repository.ReviewImgRepository
import com.psr.psr.review.repository.ReviewReportRepository
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
    private val reviewReportRepository: ReviewReportRepository
) {
    // 리뷰 생성
    @Transactional
    fun makeReview(user: User, orderId: Long, reviewReq: ReviewReq) {
        val order = orderRepository.findByIdAndStatus(orderId, ACTIVE_STATUS)
            ?: throw BaseException(BaseResponseCode.NOT_FOUND_ORDER)

        if (order.user.id != user.id) throw BaseException(BaseResponseCode.NO_PERMISSION)
        if (order.review != null) throw BaseException(BaseResponseCode.REVIEW_ALREADY_COMPLETE)
        if (order.orderStatus != OrderStatus.COMPLETED) throw BaseException(BaseResponseCode.ORDER_NOT_COMPLETE)

        val review = reviewRepository.save(Review.toEntity(order, reviewReq))
        orderRepository.save(order.setReview(review))

        if (reviewReq.imgList != null) {
            val reviewImgs = reviewReq.imgList.map { img: String -> ReviewImg.toEntity(review, img) }
            reviewImgRepository.saveAll(reviewImgs)
        }
    }

    // 리뷰 수정
    @Transactional
    fun editReview(user: User, reviewId: Long, reviewReq: ReviewReq) {
        val review: Review = reviewRepository.findByIdAndStatus(reviewId, ACTIVE_STATUS)
            ?: throw BaseException(BaseResponseCode.NOT_FOUND_REVIEW)
        if (review.order.user.id != user.id) throw BaseException(BaseResponseCode.NO_PERMISSION)

        reviewRepository.save(review.editReview(reviewReq))

        val imgUrls = review.imgs.map { i -> i.imgUrl }

        // req 이미지 존재 시
        if (reviewReq.imgList != null) {
            // 기존 리뷰 이미지 미존재 시 전체 저장
            if (imgUrls.isEmpty()) {
                val reviewImgs = reviewReq.imgList.map { img: String -> ReviewImg.toEntity(review, img) }
                reviewImgRepository.saveAll(reviewImgs)
            } else { // 기존 리뷰 이미지 존재 시
                // req 이미지가 기존 리뷰 이미지에 미존재 시 삭제
                review.imgs.forEach {
                    if (!reviewReq.imgList.contains(it.imgUrl)) reviewImgRepository.delete(it)
                }

                // 새로운 이미지 저장
                reviewReq.imgList.forEach {
                    if (!imgUrls.contains(it)) reviewImgRepository.save(ReviewImg.toEntity(review, it))
                }
            }
        }

        // req 이미지 미존재 시 전체 삭제
        if (reviewReq.imgList == null) reviewImgRepository.deleteAll(review.imgs)
    }

    // 리뷰 삭제
    @Transactional
    fun deleteReview(user: User, reviewId: Long) {
        val review: Review = reviewRepository.findByIdAndStatus(reviewId, ACTIVE_STATUS)
            ?: throw BaseException(BaseResponseCode.NOT_FOUND_REVIEW)
        if (review.order.user.id != user.id) throw BaseException(BaseResponseCode.NO_PERMISSION)

        reviewRepository.delete(review)
    }

    // 상품별 리뷰 목록 조회
    fun getProductReviews(user: User, productId: Long, pageable: Pageable): Page<ReviewListRes> {
        val product: Product = productRepository.findByIdAndStatus(productId, ACTIVE_STATUS)
            ?: throw BaseException(BaseResponseCode.NOT_FOUND_PRODUCT)

        return reviewRepository.findByProductAndStatus(product, ACTIVE_STATUS, pageable)
            .map { review -> ReviewListRes.toDto(review, user) }
    }

    // 리뷰 개별 조회
    fun getReview(user: User, reviewId: Long): ReviewRes {
        val review: Review = reviewRepository.findByIdAndStatus(reviewId, ACTIVE_STATUS)
            ?: throw BaseException(BaseResponseCode.NOT_FOUND_REVIEW)
        if (review.order.user.id != user.id) throw BaseException(BaseResponseCode.NO_PERMISSION)

        return ReviewRes.toDto(review)
    }

    // 리뷰 신고
    fun reportReview(user: User, reviewId: Long, category: String) {
        val reportCategory = ReportCategory.findByName(category)

        val review: Review = reviewRepository.findByIdAndStatus(reviewId, ACTIVE_STATUS)
            ?: throw BaseException(BaseResponseCode.NOT_FOUND_REVIEW)
        if (review.order.user == user) throw BaseException(BaseResponseCode.VALID_REVIEW_USER)
        if (reviewReportRepository.findByReviewAndUserAndStatus(review, user, ACTIVE_STATUS) != null)
            throw BaseException(BaseResponseCode.REPORT_ALREADY_COMPLETE)

        reviewReportRepository.save(ReviewReport.toEntity(review, user, reportCategory))
    }

    // 상품 조회 시 리뷰 목록 조회(5개)
    fun getProductDetail(user: User, productId: Long): GetProductDetailRes {
        val product: Product = productRepository.findByIdAndStatus(productId, ACTIVE_STATUS)
            ?: throw BaseException(BaseResponseCode.NOT_FOUND_PRODUCT)

        val reviewList = product.reviews?.sortedByDescending { it.createdAt }!!.take(5)
        return GetProductDetailRes.toDto(reviewList)
    }
}