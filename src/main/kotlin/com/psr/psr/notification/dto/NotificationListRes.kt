package com.psr.psr.notification.dto

import com.querydsl.core.annotations.QueryProjection
import io.swagger.v3.oas.annotations.media.Schema

data class NotificationListRes @QueryProjection constructor(
    @Schema(description = "알림 일자", example = "2023-01-01")
    val date: String,
    val notiList: List<NotiList>
)