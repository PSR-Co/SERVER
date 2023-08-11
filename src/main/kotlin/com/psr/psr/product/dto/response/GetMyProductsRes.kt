package com.psr.psr.product.dto.response

import org.springframework.data.domain.Page

data class GetMyProductsRes(
    val productList: Page<MyProduct>?
)
