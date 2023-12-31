package com.psr.psr.notification.repository

import com.psr.psr.notification.entity.PushNotification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NotificationRepository: JpaRepository<PushNotification, Long>, NotificationCustom {
}