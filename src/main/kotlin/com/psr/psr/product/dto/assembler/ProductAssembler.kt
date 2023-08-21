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

    fun toGetMyProductsDto(productList: Page<Product>?): GetMyProductsRes {
        return GetMyProductsRes(
            productList = productList?.map { this.toMyProductDto(it) }
        )
    }
    fun toMyProductDto(product: Product, imgUrl: String): MyProduct {
        return MyProduct(
            productId = product.id!!,
            imgUrl = imgUrl,
            category = product.category.value,
            name = product.name,
            price = product.price
        )
    }

    fun toMyProductDto(product: Product): MyProduct {
        return MyProduct(
            productId = product.id!!,
            imgUrl = product.imgs.firstOrNull()?.imgUrl,
            category = product.category.value,
            name = product.name,
            price = product.price
        )
    }

    fun toGetProductsByUserResDto(user: User, productList: Page<Product>?): GetProductsByUserRes {
        return GetProductsByUserRes(
            imgUrl = user.imgUrl,
            type = user.type.value,
            nickname = user.nickname,
            productList = productList?.map { p -> toMyProductDto(p) }
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

    fun toGetLikeProductsResDto(productList: Page<Product>?): GetLikeProductsRes {
        return GetLikeProductsRes(
            productList = productList?.map { pl -> this.toMyProductDto(pl) }
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
            popularProductList = productList?.sortedByDescending { it.likeNum?.size }!!.take(5).map { p -> this.toMainProductDto(p) }.toList()
        )
    }

    fun toMainTopProductDto(product: Product): MainTopProduct {
        return MainTopProduct(
            id = product.id!!,
            category = product.category.value,
            name = product.name,
            description = product.description
        )
    }

    fun toMainProductDto(product: Product): MainProduct {
        return MainProduct(
            id = product.id!!,
            imgUrl = product.imgs.firstOrNull()?.imgUrl,
            name = product.name
        )
    }

    fun toProductEntity(user: User, request: CreateproductReq): Product {
        return Product(
            user = user,
            name = request.name,
            category = Category.getCategoryByValue(request.category),
            price = request.price,
            description = request.description
        )
    }

    fun toProductImgEntity(product: Product, imgUrl: String): ProductImg {
        return ProductImg(
            product = product,
            imgUrl = imgUrl
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