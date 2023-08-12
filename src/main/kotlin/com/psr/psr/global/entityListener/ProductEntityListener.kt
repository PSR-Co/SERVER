package com.psr.psr.global.entityListener

import com.psr.psr.product.entity.Product
import com.psr.psr.product.repository.ProductImgRepository
import com.psr.psr.product.repository.ProductLikeRepository
import jakarta.persistence.PreRemove



class ProductEntityListener {
    @PreRemove
    fun onUpdate(product: Product) {
        val productImgRepository: ProductImgRepository = BeanUtils.getBean(ProductImgRepository::class.java)
        productImgRepository.deleteByProduct(product)
        val productLikeRepository: ProductLikeRepository = BeanUtils.getBean(ProductLikeRepository::class.java)
        productLikeRepository.deleteByProduct(product)
    }
}