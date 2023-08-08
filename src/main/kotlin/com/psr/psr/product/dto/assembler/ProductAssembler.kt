package com.psr.psr.product.dto.assembler

import com.psr.psr.product.dto.response.GetProductDetailRes
import com.psr.psr.product.dto.response.GetProductsByUserRes
import com.psr.psr.product.dto.response.MyProduct
import com.psr.psr.product.entity.Product
import com.psr.psr.product.entity.ProductImg
import com.psr.psr.user.entity.User
import org.springframework.stereotype.Component

@Component
class ProductAssembler {
    fun toMyProductDto(product: Product, imgUrl: String): MyProduct {
        return MyProduct(
            productId = product.id,
            imgUrl = imgUrl,
            category = product.category.value,
            name = product.name,
            price = product.price
        )
    }

    fun toGetProductsByUserResDto(user: User, productList: List<MyProduct>?): GetProductsByUserRes {
        return GetProductsByUserRes(
            imgUrl = user.imgUrl,
            type = user.type.value,
            nickname = user.nickname,
            productList = productList
        )
    }

    fun toGetProductDetailResDto(isOwner: Boolean, product: Product, imgList: List<ProductImg>, numOfLikes: Int, isLike: Boolean): GetProductDetailRes {
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