package com.psr.psr.product.repository.product


import com.psr.psr.product.dto.response.PopularProductDetail
import com.psr.psr.product.dto.response.ProductDetail
import com.psr.psr.product.dto.response.QPopularProductDetail
import com.psr.psr.product.dto.response.QProductDetail
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component
import com.psr.psr.product.entity.product.QProduct.product
import com.psr.psr.product.entity.product.QProductLike.productLike
import com.psr.psr.product.entity.product.QProductImg.productImg
import com.psr.psr.product.entity.review.QReview.review
import com.psr.psr.user.entity.Category
import com.psr.psr.user.entity.User
import com.querydsl.core.types.ExpressionUtils
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.JPAExpressions

@Component
class ProductRepositoryImpl(
    private val queryFactory: JPAQueryFactory
): ProductCustom {

    override fun findTop5PopularProducts(target: User, category: List<Category>): List<PopularProductDetail> {

        return queryFactory
            .select(QPopularProductDetail(
                product.id,
                JPAExpressions.select(productImg.imgKey).from(productImg).where(productImg.id.eq(JPAExpressions.select(productImg.id.min()).from(productImg).where(productImg.product.eq(product)))),
                product.name,
                product.price,
                ExpressionUtils.`as`(product.likeNum, "numOfLike"),
                Expressions.asBoolean(JPAExpressions.selectFrom(productLike).where(productLike.user.eq(target).and(productLike.product.eq(product)).and(productLike.status.eq("active"))).exists()),
                ExpressionUtils.`as`(JPAExpressions.select(review.rating.avg()).from(review).where(review.product.eq(product)), "avgOfRating"),
                ExpressionUtils.`as`(JPAExpressions.select(review.count().intValue()).from(review).where(review.product.eq(product)), "numOfReview")
                ))
            .from(product)
            .leftJoin(product).on(product.eq(productLike.product))
            .where(product.category.`in`(category))
            .groupBy(product.id)
            .orderBy(product.likeNum.desc())
            .limit(5)
            .fetch()
    }

    override fun findAllCategoryProducts(target: User, category: List<Category>): List<ProductDetail> {

        return queryFactory
            .select(QProductDetail(
                product.id,
                JPAExpressions.select(productImg.imgKey).from(productImg).where(productImg.id.eq(JPAExpressions.select(productImg.id.min()).from(productImg).where(productImg.product.eq(product)))),
                product.user.id,
                product.user.nickname,
                product.name,
                product.price,
                Expressions.asBoolean(JPAExpressions.selectFrom(productLike).where(productLike.user.eq(target).and(productLike.product.eq(product)).and(productLike.status.eq("active"))).exists())
                ))
            .from(product)
            .where(product.category.`in`(category))
            .groupBy(product.id)
            .orderBy(product.createdAt.desc())
            .fetch()

    }
}