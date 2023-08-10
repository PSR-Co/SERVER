package com.psr.psr.product.dto.response

import org.springframework.data.domain.Page

data class GetProductsByUserRes(
    val imgUrl: String?,
    val type: String,
    val nickname: String,
    val productList: Page<MyProduct>?
)
