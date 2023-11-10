package com.psr.psr.product.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.domain.Page

data class GetProductsRes(
    @Schema(description = "인기 목록")
    val popularList: List<PopularProductDetail>,
    @Schema(description = "일반 목록")
    val productList: Page<ProductDetail>
)
