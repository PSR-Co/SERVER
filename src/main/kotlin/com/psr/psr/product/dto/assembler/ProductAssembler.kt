package com.psr.psr.product.dto.assembler

import com.psr.psr.product.dto.response.MyProduct
import com.psr.psr.product.entity.product.Product
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

}