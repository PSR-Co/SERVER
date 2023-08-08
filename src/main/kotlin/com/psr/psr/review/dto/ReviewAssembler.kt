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

    fun toListDto(review: Review): ReviewListRes {
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

    fun toDto(review: Review): ReviewRes {
        val reviewImgs =
            if (review.imgs?.size != 0) review.imgs?.map { img -> img.imgUrl }
            else null
        return ReviewRes(
            reviewId = review.id!!,
            rating = review.rating,
            content = review.content,
            imgList = reviewImgs,
            nickname = review.product.user.nickname,
            productName = review.product.name,
            productImgUrl = review.product.imgs[0].imgUrl
        )
    }

}