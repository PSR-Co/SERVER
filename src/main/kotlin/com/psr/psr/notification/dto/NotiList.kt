package com.psr.psr.notification.dto

import com.querydsl.core.annotations.QueryProjection
import io.swagger.v3.oas.annotations.media.Schema

data class NotiList @QueryProjection constructor(
    @Schema(description = "알림 제목", example = "알림 제목")
    val title: String,
    @Schema(description = "알림 내용", example = "알림 내용")
    val content: String
)