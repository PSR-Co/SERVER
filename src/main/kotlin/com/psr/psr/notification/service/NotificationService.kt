package com.psr.psr.notification.service

import com.psr.psr.notification.repository.NotificationRepository
import org.springframework.stereotype.Service

@Service
class NotificationService(
        private val notificationRepository: NotificationRepository
) {
}