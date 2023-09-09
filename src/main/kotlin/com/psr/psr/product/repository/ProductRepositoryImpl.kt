package com.psr.psr.product.repository

import com.psr.psr.global.Constant.UserStatus.UserStatus.ACTIVE_STATUS
import com.psr.psr.product.dto.response.PopularProductDetail
import com.psr.psr.product.dto.response.ProductDetail
import com.psr.psr.product.dto.response.QPopularProductDetail
import com.psr.psr.product.dto.response.QProductDetail
import com.psr.psr.product.entity.QProduct.product
import com.psr.psr.product.entity.QProductImg.productImg
import com.psr.psr.product.entity.QProductLike.productLike
import com.psr.psr.review.entity.QReview.review
import com.psr.psr.user.entity.Category
import com.psr.psr.user.entity.User
import com.querydsl.core.types.ExpressionUtils
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.JPAExpressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class ProductRepositoryImpl(
    private val queryFactory: JPAQueryFactory
): ProductCustom {

    override fun findTop5PopularProducts(target: User, category: List<Category>): List<PopularProductDetail> {

        return queryFactory
            .select(QPopularProductDetail(
                product.id,
                JPAExpressions.select(productImg.imgUrl).from(productImg).where(productImg.id.eq(JPAExpressions.select(productImg.id.min()).from(productImg).where(productImg.product.eq(product).and(productImg.status.eq(ACTIVE_STATUS))))),
                product.name,
                product.price,
                ExpressionUtils.`as`(product.likeNum.size(), "numOfLike"),
                Expressions.asBoolean(JPAExpressions.selectFrom(productLike).where(productLike.user.eq(target).and(productLike.product.eq(product)).and(productLike.status.eq(ACTIVE_STATUS))).exists()),
                ExpressionUtils.`as`(JPAExpressions.select(review.rating.avg()).from(review).where(review.product.eq(product).and(review.status.eq(ACTIVE_STATUS))), "avgOfRating"),
                ExpressionUtils.`as`(product.reviews.size(), "numOfReview")
                ))
            .from(product)
            .where(product.category.`in`(category).and(product.status.eq(ACTIVE_STATUS)))
            .groupBy(product.id)
            .orderBy(product.likeNum.size().desc())
            .limit(5)
            .fetch()
    }

    override fun findAllCategoryProducts(pageable: Pageable, target: User, category: List<Category>): Page<ProductDetail> {

        val result = queryFactory
            .select(
                QProductDetail(
                    product.id,
                    JPAExpressions.select(productImg.imgUrl).from(productImg).where(productImg.id.eq(JPAExpressions.select(productImg.id.min()).from(productImg).where(productImg.product.eq(product).and(productImg.status.eq(ACTIVE_STATUS))))),
                    product.user.id,
                    product.user.nickname,
                    product.name,
                    product.price,
                    Expressions.asBoolean(JPAExpressions.selectFrom(productLike).where(productLike.user.eq(target).and(productLike.product.eq(product)).and(productLike.status.eq(ACTIVE_STATUS))).exists()
                    )
                )
            )
            .from(product)
            .where(product.category.`in`(category).and(product.status.eq(ACTIVE_STATUS)))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .groupBy(product.id)
            .orderBy(product.createdAt.desc())
            .fetch()

        return PageImpl(result, pageable, result.size.toLong())

    }

    // 인기순
    override fun searchProductsByLike(target: User, keyword: String, pageable: Pageable): Page<ProductDetail> {
        val result = queryFactory
            .select(
                QProductDetail(
                    product.id,
                    JPAExpressions.select(productImg.imgUrl).from(productImg).where(
                        productImg.id.eq(
                            JPAExpressions.select(productImg.id.min()).from(productImg)
                                .where(productImg.product.eq(product).and(productImg.status.eq(ACTIVE_STATUS)))
                        )
                    ),
                    product.user.id,
                    product.user.nickname,
                    product.name,
                    product.price,
                    Expressions.asBoolean(
                        JPAExpressions.selectFrom(productLike).where(
                            productLike.user.eq(target).and(productLike.product.eq(product))
                                .and(productLike.status.eq(ACTIVE_STATUS))
                        ).exists()
                    )
                )
            )
            .from(product)
            .where(product.name.contains(keyword).and(product.status.eq(ACTIVE_STATUS)))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .groupBy(product.id)
            .orderBy(product.likeNum.size().desc())
            .fetch()

        return PageImpl(result, pageable, result.size.toLong())

    }

    // 최신순
    override fun searchProductsByCreatedAt(target: User, keyword: String, pageable: Pageable): Page<ProductDetail> {
        val result = queryFactory
            .select(
                QProductDetail(
                    product.id,
                    JPAExpressions.select(productImg.imgUrl).from(productImg).where(
                        productImg.id.eq(
                            JPAExpressions.select(productImg.id.min()).from(productImg)
                                .where(productImg.product.eq(product).and(productImg.status.eq(ACTIVE_STATUS)))
                        )
                    ),
                    product.user.id,
                    product.user.nickname,
                    product.name,
                    product.price,
                    Expressions.asBoolean(
                        JPAExpressions.selectFrom(productLike).where(
                            productLike.user.eq(target).and(productLike.product.eq(product))
                                .and(productLike.status.eq(ACTIVE_STATUS))
                        ).exists()
                    )
                )
            )
            .from(product)
            .where(product.name.contains(keyword).and(product.status.eq(ACTIVE_STATUS)))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .groupBy(product.id)
            .orderBy(product.createdAt.desc())
            .fetch()

        return PageImpl(result, pageable, result.size.toLong())
    }
}