package com.psr.psr.cs.dto.response

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonInclude
import com.psr.psr.cs.entity.Notice
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

data class NoticeRes (
    @Schema(description = "공지사항 id", example = "1")
    val noticeId: Long,
    @Schema(description = "상품 제목", example = "2024 새해 복 많이 받으세요 ^_^")
    val title: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val date: LocalDateTime ?= null,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val imgUrl: String ?= null,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val content: String ?= null
){
    companion object{
        fun toNoticeRes(notice: Notice): NoticeRes {
            return NoticeRes(notice.id, notice.title, notice.createdAt, notice.imgUrl, notice.content)
        }

        fun toNoticeResHome(notice: Notice): NoticeRes {
            return NoticeRes(noticeId = notice.id, title = notice.title)
        }
    }
}