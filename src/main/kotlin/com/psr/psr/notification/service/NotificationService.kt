package com.psr.psr.notification.service

import com.psr.psr.notification.dto.NotificationListRes
import com.psr.psr.notification.repository.NotificationRepository
import com.psr.psr.user.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class NotificationService(
        private val notificationRepository: NotificationRepository
) {
    fun getNotiList(user: User, pageable: Pageable): Page<NotificationListRes> {
        return notificationRepository.findNotificationByUserGroupByDate(user, pageable)
    }
}