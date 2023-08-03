package com.psr.psr.product.controller

import com.psr.psr.global.dto.BaseResponse
import com.psr.psr.global.jwt.UserAccount
import com.psr.psr.product.dto.response.GetProductsByUserRes
import com.psr.psr.product.dto.response.GetProductsRes
import com.psr.psr.product.dto.response.MyProduct
import com.psr.psr.product.service.ProductService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/products")
class ProductController(
        private val productService: ProductService
) {

        /**
         * 상품 메인 화면
         */
        @GetMapping()
        fun getProducts(@AuthenticationPrincipal userAccount: UserAccount, @RequestParam(required = false) category: String): BaseResponse<GetProductsRes> {
                return BaseResponse(productService.getProducts(userAccount.getUser(), category));
        }

        /**
         * 마이 게시글
         */
        @GetMapping("/myproducts")
        fun getMyProducts(@AuthenticationPrincipal userAccount: UserAccount): BaseResponse<List<MyProduct>?> {
                return BaseResponse(productService.getMyProducts(userAccount.getUser()));
        }

        /**
         * 유저 상품 목록
         */
        @GetMapping("/users/{userId}")
        fun getProductsByUser(@AuthenticationPrincipal userAccount: UserAccount, @PathVariable userId: Long): BaseResponse<GetProductsByUserRes> {
                return BaseResponse(productService.getProductsByUser(userId))
        }


}