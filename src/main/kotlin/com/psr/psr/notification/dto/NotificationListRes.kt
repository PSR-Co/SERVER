package com.psr.psr.notification.dto

import com.querydsl.core.annotations.QueryProjection

data class NotificationListRes @QueryProjection constructor(
    val date: String,
    val notiList: List<NotiList>
)