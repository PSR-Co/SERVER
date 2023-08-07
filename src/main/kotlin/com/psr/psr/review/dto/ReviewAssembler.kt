package com.psr.psr.review.dto

import com.psr.psr.global.entity.ReportCategory
import com.psr.psr.order.entity.Order
import com.psr.psr.product.entity.Product
import com.psr.psr.review.entity.Review
import com.psr.psr.review.entity.ReviewImg
import com.psr.psr.review.entity.ReviewReport
import com.psr.psr.user.entity.User
import org.springframework.stereotype.Component
import java.time.format.DateTimeFormatter

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

    fun toImgEntity(review: Review, imgKey: String): ReviewImg {
        return ReviewImg(
            review = review,
            imgKey = imgKey
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
            if (review.imgs?.size != 0) review.imgs?.map { img -> img.imgKey }
            else null
        return ReviewListRes(
            reviewId = review.id!!,
            rating = review.rating,
            content = review.content,
            imgList = reviewImgs,
            reviewedDate = review.createdAt.format(DateTimeFormatter.ISO_DATE),
            nickName = review.order.user.nickname,
            profileImgKey = review.order.user.imgKey
        )
    }

}