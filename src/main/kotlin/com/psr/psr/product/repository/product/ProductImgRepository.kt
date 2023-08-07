package com.psr.psr.product.repository.product


import com.psr.psr.product.entity.product.Product
import com.psr.psr.product.entity.product.ProductImg
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductImgRepository: JpaRepository<ProductImg, Long> {
    fun findTop1ByProductAndStatusOrderByCreatedAtDesc(product: Product, status: String): ProductImg
}