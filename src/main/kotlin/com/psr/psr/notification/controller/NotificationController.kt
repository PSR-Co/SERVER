package com.psr.psr.notification.controller

import com.psr.psr.notification.service.NotificationService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/notification")
class NotificationController(
        private val notificationService: NotificationService
) {
}