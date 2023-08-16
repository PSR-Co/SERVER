package com.psr.psr.notification.controller

import com.psr.psr.global.dto.BaseResponse
import com.psr.psr.global.jwt.UserAccount
import com.psr.psr.notification.dto.NotificationListRes
import com.psr.psr.notification.service.NotificationService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/notifications")
class NotificationController(
    private val notificationService: NotificationService
) {
    @GetMapping
    fun getNotiList(
        @AuthenticationPrincipal userAccount: UserAccount,
        @PageableDefault(size = 8) pageable: Pageable  // 8일치 알림
    ): BaseResponse<Page<NotificationListRes>> {
        return BaseResponse(notificationService.getNotiList(userAccount.getUser(), pageable))
    }
}