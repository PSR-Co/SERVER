package com.psr.psr.product.repository.product

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component

@Component
class ProductRepositoryImpl(
    private val queryFactory: JPAQueryFactory
): ProductCustom {
}