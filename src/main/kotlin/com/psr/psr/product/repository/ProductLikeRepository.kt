package com.psr.psr.product.repository

import com.psr.psr.product.entity.ProductLike
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductLikeRepository: JpaRepository<ProductLike, Long> {
}