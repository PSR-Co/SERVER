package com.psr.psr.product.dto.assembler

import com.psr.psr.global.entity.ReportCategory
import com.psr.psr.product.dto.response.*
import com.psr.psr.product.entity.Product
import com.psr.psr.product.entity.ProductImg
import com.psr.psr.product.entity.ProductLike
import com.psr.psr.product.entity.ProductReport
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

    fun toMyProductDto(product: Product): MyProduct {
        val imgUrl =
            if (product.imgs.isNotEmpty()) product.imgs[0].imgUrl
            else null
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

    fun toGetLikeProductsResDto(productLikeList: List<ProductLike>?): GetLikeProductsRes {
        return GetLikeProductsRes(
            productList = productLikeList?.map { pl -> this.toMyProductDto(pl.product) }?.toList()
        )

    }

    fun toReportEntity(product: Product, user: User, category: ReportCategory): ProductReport {
        return ProductReport(
            product = product,
            user = user,
            category = category
        )
    }

    fun toGetHomePageResDto(mainTopProductList: List<Product>?, productList: List<Product>?): GetHomePageRes {
        return GetHomePageRes(
            mainTopProductList = mainTopProductList?.sortedByDescending { it.createdAt }!!.take(3).map { p -> this.toMainTopProductDto(p) }.toList(),
            recentProductList = productList?.sortedByDescending { it.createdAt }!!.take(5).map { p -> this.toMainProductDto(p) }.toList(),
            popularProductList = productList?.sortedByDescending { it.likeNum }!!.take(5).map { p -> this.toMainProductDto(p) }.toList()
        )
    }

    fun toMainTopProductDto(product: Product): MainTopProduct {
        return MainTopProduct(
            id = product.id,
            category = product.category.value,
            name = product.name,
            description = product.description
        )
    }

    fun toMainProductDto(product: Product): MainProduct {
        val imgUrl =
            if (product.imgs.isNotEmpty()) product.imgs[0].imgUrl
            else null
        return MainProduct(
            id = product.id,
            imgUrl = imgUrl,
            name = product.name
        )
    }


}