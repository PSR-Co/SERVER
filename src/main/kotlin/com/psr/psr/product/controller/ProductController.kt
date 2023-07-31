package com.psr.psr.product.controller

import com.psr.psr.global.dto.BaseResponse
import com.psr.psr.global.jwt.UserAccount
import com.psr.psr.product.dto.response.GetProductsRes
import com.psr.psr.product.service.ProductService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
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


}