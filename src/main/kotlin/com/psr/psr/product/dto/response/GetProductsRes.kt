package com.psr.psr.product.dto.response

data class GetProductsRes(
    val popularList: List<PopularProductDetail>,
    val productList: List<ProductDetail>
)
