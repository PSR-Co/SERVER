package com.psr.psr.notification.dto

import com.querydsl.core.annotations.QueryProjection

data class NotiList @QueryProjection constructor(
    val title: String,
    val content: String
)