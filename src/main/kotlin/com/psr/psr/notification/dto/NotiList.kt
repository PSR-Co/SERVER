package com.psr.psr.notification.dto

import com.querydsl.core.annotations.QueryProjection

data class NotiList @QueryProjection constructor(
    val productName: String,
    val content: String
)