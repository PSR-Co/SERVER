package com.psr.psr.product.dto.assembler

import com.psr.psr.product.dto.response.GetProductsByUserRes
import com.psr.psr.product.dto.response.MyProduct
import com.psr.psr.product.entity.product.Product
import com.psr.psr.user.entity.User
import org.springframework.stereotype.Component

@Component
class ProductAssembler {
    fun toMyProductDto(product: Product, imgKey: String): MyProduct {
        return MyProduct(
            productId = product.id,
            imgKey = imgKey,
            category = product.category.value,
            name = product.name,
            price = product.price
        )
    }

    fun toGetProductsByUserResDto(user: User, productList: List<MyProduct>?): GetProductsByUserRes {
        return GetProductsByUserRes(
            imgKey = user.imgKey,
            type = user.type.value,
            nickname = user.nickname,
            productList = productList
        )
    }


}