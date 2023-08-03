package com.psr.psr.product.service

import com.psr.psr.global.Constant.USER_STATUS.USER_STATUS.ACTIVE_STATUS
import com.psr.psr.product.dto.assembler.ProductAssembler
import com.psr.psr.product.dto.response.GetProductsRes
import com.psr.psr.product.dto.response.MyProduct
import com.psr.psr.product.entity.product.Product
import com.psr.psr.product.repository.product.ProductImgRepository
import com.psr.psr.product.repository.product.ProductRepository
import com.psr.psr.user.entity.Category
import com.psr.psr.user.entity.User
import com.psr.psr.user.repository.UserInterestRepository
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val userInterestRepository: UserInterestRepository,
    private val productImgRepository: ProductImgRepository,
    private val productAssembler: ProductAssembler
) {
    fun getProducts(user: User, category: String): GetProductsRes {
        var interestCategoryList: MutableList<Category> = ArrayList()
        if(category.isEmpty()) {
            // 유저의 관심목록
            val userInterestList = userInterestRepository.findByUser(user);
            interestCategoryList = userInterestList.stream().map { ui -> ui.category }.toList()
        } else {
            interestCategoryList.add(Category.getCategoryByName(category))
        }

        val productList = productRepository.findAllCategoryProducts(user, interestCategoryList)
        val popularList = productRepository.findTop5PopularProducts(user, interestCategoryList)

        return GetProductsRes(popularList, productList)
    }

    fun getMyProducts(user: User): List<MyProduct>? {
        val myProductList: List<Product>? = productRepository.findAllByUserAndStatusOrderByCreatedAtDesc(user, ACTIVE_STATUS)

        return myProductList?.map { p: Product ->
            val productImg = productImgRepository.findTop1ByProductEqualsAndStatusEqualsOrderByCreatedAtDesc(p, ACTIVE_STATUS)
            productAssembler.toMyProductDto(p, productImg.imgKey)
        }
    }

}