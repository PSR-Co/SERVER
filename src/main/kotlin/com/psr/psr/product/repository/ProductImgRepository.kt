package com.psr.psr.product.repository


import com.psr.psr.product.entity.Product
import com.psr.psr.product.entity.ProductImg
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductImgRepository: JpaRepository<ProductImg, Long> {
    fun findTop1ByProductAndStatusOrderByCreatedAtDesc(product: Product, status: String): ProductImg
    fun findByProductAndStatus(product: Product, activeStatus: String): List<ProductImg>
    fun deleteByProduct(product: Product)

}