package com.psr.psr.review.dto

import com.psr.psr.global.entity.ReportCategory
import com.psr.psr.order.entity.Order
import com.psr.psr.review.entity.Review
import com.psr.psr.review.entity.ReviewImg
import com.psr.psr.review.entity.ReviewReport
import com.psr.psr.user.entity.User
import org.springframework.stereotype.Component
import java.time.format.DateTimeFormatter

@Component
class ReviewAssembler {
    fun toEntity(order: Order, reviewReq: ReviewReq): Review {
        return Review(
            order = order,
            product = order.product,
            rating = reviewReq.rating,
            content = reviewReq.content
        )
    }

    fun toImgEntity(review: Review, imgUrl: String): ReviewImg {
        return ReviewImg(
            review = review,
            imgUrl = imgUrl
        )
    }

    fun toReportEntity(review: Review, user: User, reportCategory: ReportCategory): ReviewReport {
        return ReviewReport(
            review = review,
            category = reportCategory,
            user = user
        )
    }

    fun toDto(review: Review): ReviewListRes {
        val reviewImgs =
            if (review.imgs?.size != 0) review.imgs?.map { img -> img.imgUrl }
            else null
        return ReviewListRes(
            reviewId = review.id!!,
            rating = review.rating,
            content = review.content,
            imgList = reviewImgs,
            reviewedDate = review.createdAt.format(DateTimeFormatter.ISO_DATE),
            nickName = review.order.user.nickname,
            profileImgUrl = review.order.user.imgUrl
        )
    }

    fun toGetProductDetailResDto(reviewList: List<Review>): GetProductDetailRes {
        val ratingList = reviewList.map { r -> r.rating }
        return GetProductDetailRes(
            numOfReviews = reviewList.size,
            avgOfRating = ratingList.average(),
            reviewList = reviewList.map { r -> this.toReviewDetailTopDto(r) }.toList()
        )
    }

    private fun toReviewDetailTopDto(review: Review): ReviewDetailTop {
        val imgUrl =
            if (review.imgs?.size != 0) review.imgs!![0].imgUrl
            else null
        return ReviewDetailTop(
                imgUrl = imgUrl,
                rating = review.rating,
                content = review.content
            )
    }
}