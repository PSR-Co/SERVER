package com.psr.psr.product.service

import com.psr.psr.global.Constant.UserStatus.UserStatus.ACTIVE_STATUS
import com.psr.psr.global.entity.ReportCategory
import com.psr.psr.global.exception.BaseException
import com.psr.psr.global.exception.BaseResponseCode
import com.psr.psr.product.dto.assembler.ProductAssembler
import com.psr.psr.product.dto.request.CreateproductReq
import com.psr.psr.product.dto.response.*
import com.psr.psr.product.entity.Product
import com.psr.psr.product.repository.ProductImgRepository
import com.psr.psr.product.repository.ProductLikeRepository
import com.psr.psr.product.repository.ProductReportRepository
import com.psr.psr.product.repository.ProductRepository
import com.psr.psr.user.entity.Category
import com.psr.psr.user.entity.Type
import com.psr.psr.user.entity.User
import com.psr.psr.user.repository.UserInterestRepository
import com.psr.psr.user.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val userInterestRepository: UserInterestRepository,
    private val productImgRepository: ProductImgRepository,
    private val productLikeRepository: ProductLikeRepository,
    private val productReportRepository: ProductReportRepository,
    private val userRepository: UserRepository,
    private val productAssembler: ProductAssembler
) {
    fun getProducts(user: User, category: String, pageable: Pageable): GetProductsRes {
        var interestCategoryList: MutableList<Category> = ArrayList()
        if(category.isEmpty()) {
            // 유저의 관심목록
            val userInterestList = userInterestRepository.findByUserAndStatus(user, ACTIVE_STATUS)
            interestCategoryList = userInterestList.stream().map { ui -> ui.category }.toList()
        } else {
            interestCategoryList.add(Category.getCategoryByName(category))
        }

        val productList = productRepository.findAllCategoryProducts(pageable, user, interestCategoryList)
        val popularList = productRepository.findTop5PopularProducts(user, interestCategoryList)

        return GetProductsRes(popularList, productList)
    }

    fun getMyProducts(user: User, pageable: Pageable): GetMyProductsRes {
        val myProductList: Page<Product>? = productRepository.findAllByUserAndStatusOrderByCreatedAtDesc(user, ACTIVE_STATUS, pageable)
        return productAssembler.toGetMyProductsDto(myProductList)
    }

    fun getProductsByUser(userId: Long, pageable: Pageable): GetProductsByUserRes {
        val user: User = userRepository.findByIdAndStatus(userId, ACTIVE_STATUS) ?: throw BaseException(BaseResponseCode.NOT_FOUND_USER)
        val products: Page<Product>? = productRepository.findAllByUserAndStatusOrderByCreatedAtDesc(user, ACTIVE_STATUS, pageable)

        val productList = products?.map { p: Product ->
            val productImg = productImgRepository.findTop1ByProductAndStatusOrderByCreatedAtDesc(p, ACTIVE_STATUS)
            productAssembler.toMyProductDto(p, productImg.imgUrl)
        }
        return productAssembler.toGetProductsByUserResDto(user, productList)
    }

    fun getProductDetail(user: User, productId: Long): GetProductDetailRes {
        val product: Product = productRepository.findByIdAndStatus(productId, ACTIVE_STATUS) ?: throw BaseException(BaseResponseCode.NOT_FOUND_PRODUCT)
        val isOwner = product.user == user
        val imgList = productImgRepository.findByProductAndStatus(product, ACTIVE_STATUS)
        val numOfLikes = productLikeRepository.countByProductAndStatus(product, ACTIVE_STATUS)
        val isLike = productLikeRepository.existsByProductAndUserAndStatus(product, user, ACTIVE_STATUS)

        return productAssembler.toGetProductDetailResDto(isOwner, product, imgList, numOfLikes, isLike)
    }

    fun reportProduct(user: User, productId: Long, category: ReportCategory) {
        val product: Product = productRepository.findByIdAndStatus(productId, ACTIVE_STATUS)
            ?: throw BaseException(BaseResponseCode.NOT_FOUND_PRODUCT)
        if (productReportRepository.findByProductAndUserAndStatus(product, user, ACTIVE_STATUS) != null)
            throw BaseException(BaseResponseCode.REPORT_ALREADY_COMPLETE)

        productReportRepository.save(productAssembler.toReportEntity(product, user, category))
    }

    fun getLikeProducts(user: User, pageable: Pageable): GetLikeProductsRes {
        val productList: Page<Product>? = productLikeRepository.findByUserAndStatus(user, ACTIVE_STATUS, pageable)?.map { it.product }
        return productAssembler.toGetLikeProductsResDto(productList)
    }

    fun getHomePage(): GetHomePageRes {
        // MANAGER 상품
        val mainTopProductList = userRepository.findByTypeAndStatus(Type.MANAGER, ACTIVE_STATUS)!!.products
        val productList = productRepository.findAllByStatus(ACTIVE_STATUS)
        return productAssembler.toGetHomePageResDto(mainTopProductList, productList)
    }

    @Transactional
    fun createProduct(user: User, request: CreateproductReq) {
        val product = productRepository.save(productAssembler.toProductEntity(user, request))
        // 이미지 있는 경우만 저장
        if(request.imgList != null)
            request.imgList.map { imgUrl: String -> productImgRepository.save(productAssembler.toProductImgEntity(product, imgUrl)) }
    }
}