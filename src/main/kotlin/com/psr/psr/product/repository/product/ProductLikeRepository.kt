package com.psr.psr.product.repository.product

import com.psr.psr.product.entity.product.ProductLike
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductLikeRepository: JpaRepository<ProductLike, Long> {
}