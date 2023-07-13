package com.psr.psr.product.controller

import com.psr.psr.product.service.ProductService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/products")
class ProductController(
        private val productService: ProductService
) {
}