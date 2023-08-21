package com.psr.psr.product.repository

import com.psr.psr.product.dto.response.PopularProductDetail
import com.psr.psr.product.dto.response.ProductDetail
import com.psr.psr.user.entity.Category
import com.psr.psr.user.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable


interface ProductCustom {
    fun findTop5PopularProducts(user: User, category: List<Category>): List<PopularProductDetail>
    fun findAllCategoryProducts(pageable: Pageable, user: User, category: List<Category>): Page<ProductDetail>
    fun searchProductsByLike(user:User, keyword: String, pageable: Pageable): Page<ProductDetail>
    fun searchProductsByCreatedAt(user:User, keyword: String, pageable: Pageable): Page<ProductDetail>
}