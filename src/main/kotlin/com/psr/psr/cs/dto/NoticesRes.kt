package com.psr.psr.cs.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class NoticesRes (
    val noticeIdx: Long,
    val title: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val date: LocalDateTime
)