package com.psr.psr.product.dto.response

import org.springframework.data.domain.Page

data class GetLikeProductsRes(
    val productList: Page<MyProduct>?
)
