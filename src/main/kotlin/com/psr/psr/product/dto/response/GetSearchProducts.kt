package com.psr.psr.product.dto.response

import org.springframework.data.domain.Page

data class GetSearchProducts(
    val productList: Page<ProductDetail>
)
