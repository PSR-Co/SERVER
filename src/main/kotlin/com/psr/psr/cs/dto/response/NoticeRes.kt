package com.psr.psr.cs.dto.response

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime

data class NoticeRes (
    val noticeId: Long,
    val title: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val date: LocalDateTime ?= null,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val imgUrl: String ?= null,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val content: String ?= null
)