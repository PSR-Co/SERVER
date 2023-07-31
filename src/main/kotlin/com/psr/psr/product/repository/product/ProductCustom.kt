package com.psr.psr.product.repository.product

import com.psr.psr.product.dto.response.PopularProductDetail
import com.psr.psr.product.dto.response.ProductDetail
import com.psr.psr.user.entity.Category
import com.psr.psr.user.entity.User

interface ProductCustom {
    fun findTop5PopularProducts(user: User, category: List<Category>): List<PopularProductDetail>
    fun findAllCategoryProducts(user: User, category: List<Category>): List<ProductDetail>
}