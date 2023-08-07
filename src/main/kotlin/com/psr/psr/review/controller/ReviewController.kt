package com.psr.psr.review.controller

import com.psr.psr.product.service.ProductService
import org.springframework.web.bind.annotation.*

@RestController
class ReviewController(
        private val productService: ProductService
) {

}