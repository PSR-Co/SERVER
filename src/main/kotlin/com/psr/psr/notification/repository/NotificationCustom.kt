package com.psr.psr.notification.repository

import com.psr.psr.notification.dto.NotificationListRes
import com.psr.psr.user.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface NotificationCustom {
    fun findNotificationByUserGroupByDate(user: User, pageable: Pageable): Page<NotificationListRes>
}