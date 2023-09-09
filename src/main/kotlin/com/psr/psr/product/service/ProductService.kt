package com.psr.psr.product.service

import com.psr.psr.global.Constant.SortType.OrderType.POPULAR
import com.psr.psr.global.Constant.UserStatus.UserStatus.ACTIVE_STATUS
import com.psr.psr.global.Constant.UserStatus.UserStatus.INACTIVE_STATUS
import com.psr.psr.global.entity.ReportCategory
import com.psr.psr.global.exception.BaseException
import com.psr.psr.global.exception.BaseResponseCode
import com.psr.psr.product.dto.request.CreateproductReq
import com.psr.psr.product.dto.response.*
import com.psr.psr.product.entity.Product
import com.psr.psr.product.entity.ProductImg
import com.psr.psr.product.entity.ProductLike
import com.psr.psr.product.entity.ProductReport
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
) {

    // 상품 메인 조회
    fun getProducts(user: User, category: String, pageable: Pageable): GetProductsRes {
        var interestCategoryList: MutableList<Category> = ArrayList()
        // 카테고리를 선택하지 않았을 때, 유저의 관심목록에 해당하는 게시글
        if(category.isEmpty()) {
            val userInterestList = userInterestRepository.findByUserAndStatus(user, ACTIVE_STATUS)
            interestCategoryList = userInterestList.stream().map { ui -> ui.category }.toList()
        } else {
            interestCategoryList.add(Category.getCategoryByName(category))
        }

        // 카테고리 상품들
        val productList = productRepository.findAllCategoryProducts(pageable, user, interestCategoryList)
        // 인기순(하트순) 상위 5개 상품들
        val popularList = productRepository.findTop5PopularProducts(user, interestCategoryList)

        return GetProductsRes(popularList, productList)
    }

    // 마이 게시글
    fun getMyProducts(user: User, pageable: Pageable): GetMyProductsRes {
        val myProductList: Page<Product>? = productRepository.findAllByUserAndStatusOrderByCreatedAtDesc(user, ACTIVE_STATUS, pageable)
        return GetMyProductsRes.toDto(myProductList)
    }

    // 유저 상품 목록
    fun getProductsByUser(userId: Long, pageable: Pageable): GetProductsByUserRes {
        val user: User = userRepository.findByIdAndStatus(userId, ACTIVE_STATUS) ?: throw BaseException(BaseResponseCode.NOT_FOUND_USER)
        val products: Page<Product>? = productRepository.findAllByUserAndStatusOrderByCreatedAtDesc(user, ACTIVE_STATUS, pageable)

        return GetProductsByUserRes.toDto(user, products)
    }

    // 상품 상세 조회 - 상품
    fun getProductDetail(user: User, productId: Long): GetProductDetailRes {
        val product: Product = productRepository.findByIdAndStatus(productId, ACTIVE_STATUS) ?: throw BaseException(BaseResponseCode.NOT_FOUND_PRODUCT)
        val isOwner = product.user == user
        val imgList = productImgRepository.findByProductAndStatus(product, ACTIVE_STATUS)
        val numOfLikes = productLikeRepository.countByProductAndStatus(product, ACTIVE_STATUS)
        val isLike = productLikeRepository.existsByProductAndUserAndStatus(product, user, ACTIVE_STATUS)

        return GetProductDetailRes.toDto(isOwner, product, imgList, numOfLikes, isLike)
    }

    // 상품 신고
    fun reportProduct(user: User, productId: Long, category: ReportCategory) {
        val product: Product = productRepository.findByIdAndStatus(productId, ACTIVE_STATUS)
            ?: throw BaseException(BaseResponseCode.NOT_FOUND_PRODUCT)
        // 신고한 글은 재신고 불가
        if (productReportRepository.findByProductAndUserAndStatus(product, user, ACTIVE_STATUS) != null)
            throw BaseException(BaseResponseCode.REPORT_ALREADY_COMPLETE)

        productReportRepository.save(ProductReport.toEntity(product, user, category))
    }

    // 찜 목록
    fun getLikeProducts(user: User, pageable: Pageable): GetLikeProductsRes {
        val productList: Page<Product>? = productLikeRepository.findByUserAndStatus(user, ACTIVE_STATUS, pageable)?.map { it.product }
        return GetLikeProductsRes.toDto(productList)
    }

    // 홈 화면 조회 - 상품
    fun getHomePage(): GetHomePageRes {
        // MANAGER 상품
        val mainTopProductList = userRepository.findByTypeAndStatus(Type.MANAGER, ACTIVE_STATUS)!!.products
        // 전체 상품
        val productList = productRepository.findAllByStatus(ACTIVE_STATUS)
        return GetHomePageRes.toDto(mainTopProductList, productList)
    }

    // 상품 등록
    @Transactional
    fun createProduct(user: User, request: CreateproductReq) {
        val product = productRepository.save(Product.toEntity(user, request))
        // 이미지 있는 경우만 저장
        if(request.imgList != null)
            request.imgList.map { imgUrl: String -> productImgRepository.save(ProductImg.toEntity(product, imgUrl)) }
    }

    // 상품 찜
    fun likeProduct(user: User, productId: Long) {
        val product: Product = productRepository.findByIdAndStatus(productId, ACTIVE_STATUS)
            ?: throw BaseException(BaseResponseCode.NOT_FOUND_PRODUCT)
        val productLike = productLikeRepository.findByProductAndUser(product, user)

        // 없는 경우 새로 생성
        if (productLike == null) {
            val newProductLike = ProductLike.toEntity(product, user)
            productLikeRepository.save(newProductLike)
        } else {
            val status = productLike.status
            // 있는 경우 현재 상태와 반대로 변경
            productLike.status = if (status == ACTIVE_STATUS) INACTIVE_STATUS
                                else ACTIVE_STATUS
            productLikeRepository.save(productLike)
        }
    }

    // 상품 삭제
    fun deleteProduct(user: User, productId: Long) {
        val product: Product = productRepository.findByIdAndStatus(productId, ACTIVE_STATUS)
            ?: throw BaseException(BaseResponseCode.NOT_FOUND_PRODUCT)
        // 해당 글의 작성자 여부 확인
        if(product.user != user) throw BaseException(BaseResponseCode.INVALID_PRODUCT_USER)
        productRepository.deleteById(productId)
    }

    // 상품 검색
    fun searchProducts(user: User, keyword: String, sortType: String, pageable: Pageable): GetSearchProducts {
        // 인기순
        val productList: Page<ProductDetail> = if(sortType == POPULAR) {
            productRepository.searchProductsByLike(user, keyword, pageable)
        }
        // 최신순 (default)
        else {
            productRepository.searchProductsByCreatedAt(user, keyword, pageable)
        }
        return GetSearchProducts.toDto(productList)
    }

    // 상품 수정
    @Transactional
    fun modifyProduct(user: User, productId: Long, request: CreateproductReq) {
        val product: Product = productRepository.findByIdAndStatus(productId, ACTIVE_STATUS)
            ?: throw BaseException(BaseResponseCode.NOT_FOUND_PRODUCT)
        // 해당 글의 작성자 여부 확인
        if(product.user != user) throw BaseException(BaseResponseCode.INVALID_PRODUCT_USER)

        product.modifyProduct(request)
        // 이미지 전체 삭제 후 재저장
        productImgRepository.deleteByProduct(product)
        // 이미지가 있는 경우 저장
        if (request.imgList != null) {
            val imgList = request.imgList.map { ProductImg.toEntity(product, it) }
            productImgRepository.saveAll(imgList)
        }
    }
}