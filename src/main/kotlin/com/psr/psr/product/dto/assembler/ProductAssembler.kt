package com.psr.psr.product.dto.assembler

import com.psr.psr.global.entity.ReportCategory
import com.psr.psr.product.dto.request.CreateproductReq
import com.psr.psr.product.dto.response.*
import com.psr.psr.product.entity.Product
import com.psr.psr.product.entity.ProductImg
import com.psr.psr.product.entity.ProductLike
import com.psr.psr.product.entity.ProductReport
import com.psr.psr.user.entity.Category
import com.psr.psr.user.entity.User
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component

@Component
class ProductAssembler {


    fun toProductEntity(user: User, request: CreateproductReq): Product {
        return Product(
            user = user,
            name = request.name,
            category = Category.getCategoryByValue(request.category),
            price = request.price,
            description = request.description
        )
    }


    fun toProductLikeEntity(product: Product, user: User): ProductLike {
        return ProductLike(
            product = product,
            user = user
        )
    }

    fun toGetSearchProductsDto(productList: Page<ProductDetail>): GetSearchProducts {
        return GetSearchProducts(
            productList = productList
        )
    }


}