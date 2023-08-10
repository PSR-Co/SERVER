package com.psr.psr.product.controller

import com.psr.psr.global.dto.BaseResponse
import com.psr.psr.global.entity.ReportCategory
import com.psr.psr.global.jwt.UserAccount
import com.psr.psr.product.dto.request.ReportProductReq
import com.psr.psr.product.dto.response.GetProductDetailRes
import com.psr.psr.product.dto.response.GetProductsByUserRes
import com.psr.psr.product.dto.response.GetProductsRes
import com.psr.psr.product.dto.response.MyProduct
import com.psr.psr.product.dto.response.*
import com.psr.psr.product.service.ProductService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/products")
class ProductController(
        private val productService: ProductService
) {

        /**
         * 상품 메인 조회
         */
        @GetMapping()
        fun getProducts(@AuthenticationPrincipal userAccount: UserAccount,
                        @RequestParam(required = false) category: String,
                        @PageableDefault(size = 8) pageable: Pageable): BaseResponse<GetProductsRes> {
                return BaseResponse(productService.getProducts(userAccount.getUser(), category, pageable));
        }

        /**
         * 상품 상세 조회 - 상품
         */
        @GetMapping("/{productId}")
        fun getProductDetail(@AuthenticationPrincipal userAccount: UserAccount,
                             @PathVariable productId: Long): BaseResponse<GetProductDetailRes> {
                return BaseResponse(productService.getProductDetail(userAccount.getUser(), productId));
        }

        /**
         * 마이 게시글
         */
        @GetMapping("/myproducts")
        fun getMyProducts(@AuthenticationPrincipal userAccount: UserAccount,
                          @PageableDefault(size = 10) pageable: Pageable): BaseResponse<Page<MyProduct>?> {
                return BaseResponse(productService.getMyProducts(userAccount.getUser(), pageable));
        }

        /**
         * 유저 상품 목록
         */
        @GetMapping("/users/{userId}")
        fun getProductsByUser(@PathVariable userId: Long,
                              @PageableDefault(size = 10) pageable: Pageable): BaseResponse<GetProductsByUserRes> {
                return BaseResponse(productService.getProductsByUser(userId, pageable))
        }

        /**
         * 찜 목록
         */
        @GetMapping("/likes")
        fun getLikeProducts(@AuthenticationPrincipal userAccount: UserAccount,
                            @PageableDefault(size = 10) pageable: Pageable): BaseResponse<GetLikeProductsRes> {
                return BaseResponse(productService.getLikeProducts(userAccount.getUser(), pageable))
        }

        /**
         * 상품 신고
         */
        @PostMapping("/products/{productId}/report")
        fun reportProduct(@AuthenticationPrincipal userAccount: UserAccount,
                          @PathVariable productId: Long,
                          @RequestBody @Valid request: ReportProductReq): BaseResponse<Unit> {
                val category = ReportCategory.findByValue(request.category)
                return BaseResponse(productService.reportProduct(userAccount.getUser(), productId, category))
        }

        /**
         * 홈 화면 조회 - 상품
         */
        @GetMapping("/home")
        fun getHomePage(): BaseResponse<GetHomePageRes> {
                return BaseResponse(productService.getHomePage())
        }

}