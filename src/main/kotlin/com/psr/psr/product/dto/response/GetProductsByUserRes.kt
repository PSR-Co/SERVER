package com.psr.psr.product.dto.response

import com.psr.psr.product.entity.Product
import com.psr.psr.user.entity.User
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.domain.Page

data class GetProductsByUserRes(
    @Schema(description = "유저 프로필 이미지", example = "url")
    val imgUrl: String?,
    @Schema(description = "유저 종류", example = "일반", allowableValues = ["일반", "사업자", "쇼호스트", "관리자"])
    val type: String,
    @Schema(description = "유저 닉네임", example = "소징")
    val nickname: String,
    @Schema(description = "상품 리스트")
    val productList: Page<MyProduct>?
) {
    companion object {
        fun toDto(user: User, productList: Page<Product>?): GetProductsByUserRes {
            return GetProductsByUserRes(
                imgUrl = user.imgUrl,
                type = user.type.value,
                nickname = user.nickname,
                productList = productList?.map { p -> MyProduct.toDto(p) }
            )
        }
    }
}
