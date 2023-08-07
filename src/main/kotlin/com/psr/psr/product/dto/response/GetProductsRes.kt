package com.psr.psr.product.dto.response

import org.springframework.data.domain.Page

data class GetProductsRes(
    val popularList: List<PopularProductDetail>,
    val productList: Page<ProductDetail>
)
