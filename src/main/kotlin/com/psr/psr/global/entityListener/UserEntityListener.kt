package com.psr.psr.global.entityListener

import com.psr.psr.chat.repository.ChatRoomRepository
import com.psr.psr.order.repository.OrderRepository
import com.psr.psr.product.repository.ProductRepository
import com.psr.psr.review.repository.ReviewRepository
import com.psr.psr.user.entity.Type
import com.psr.psr.user.entity.User
import com.psr.psr.user.repository.BusinessInfoRepository
import com.psr.psr.user.repository.UserInterestRepository
import jakarta.persistence.PreRemove

class UserEntityListener {
    @PreRemove
    fun onUpdate(user: User){
        // review 삭제
        val reviewRepository: ReviewRepository = BeanUtils.getBean(ReviewRepository::class.java)
        reviewRepository.deleteByOrder_User(user)
        // order 삭제
        val orderRepository: OrderRepository = BeanUtils.getBean(OrderRepository::class.java)
        orderRepository.deleteByUser(user)
        // product 삭제
        val productRepository: ProductRepository = BeanUtils.getBean(ProductRepository::class.java)
        productRepository.deleteByUser(user)
        // chatRoom 삭제
        val chatRoomRepository: ChatRoomRepository = BeanUtils.getBean(ChatRoomRepository::class.java)
        chatRoomRepository.deleteBySenderUser(user)
        chatRoomRepository.deleteByReceiverUser(user)
        // user interest 삭제
        val userInterestRepository: UserInterestRepository = BeanUtils.getBean(UserInterestRepository::class.java)
        userInterestRepository.deleteByUser(user)
        // user가 business 인 경우 businessInfo 삭제
        if(user.type == Type.ENTREPRENEUR){
            val businessInfoRepository: BusinessInfoRepository = BeanUtils.getBean(BusinessInfoRepository::class.java)
            businessInfoRepository.deleteByUser(user)
        }
    }
}