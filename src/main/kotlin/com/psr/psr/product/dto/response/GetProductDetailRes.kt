package com.psr.psr.product.dto.response

import com.psr.psr.product.entity.Product
import com.psr.psr.product.entity.ProductImg

data class GetProductDetailRes(
    val isOwner: Boolean,
    val category: String,
    val imgList: List<String>,
    val userId: Long?,
    val nickname: String,
    val numOfLikes: Int,
    val name: String,
    val price: Int,
    val description: String,
    val isLike: Boolean
) {
    companion object {
        fun toDto(isOwner: Boolean, product: Product, imgList: List<ProductImg>, numOfLikes: Int, isLike: Boolean): GetProductDetailRes {
            return GetProductDetailRes(
                isOwner = isOwner,
                category = product.category.value,
                imgList = imgList.map { i -> i.imgUrl }.toList(),
                userId = product.user.id,
                nickname = product.user.nickname,
                numOfLikes = numOfLikes,
                name = product.name,
                price = product.price,
                description = product.description,
                isLike = isLike
            )
        }
    }
}
