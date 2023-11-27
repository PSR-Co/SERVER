package com.psr.psr.chat.dto.response

import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

data class ChatMessagesRes(
    @Schema(description = "최근 메시지 날짜", example = "11.30")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY년 MM월 dd일")
    val date: LocalDate? = null,

    @Schema(description = "메시지 리스트")
    val chatMessages: List<ChatMessageRes>?
) {
    companion object {
        fun toDto(standardDate: LocalDate, messagesOfDate: List<ChatMessageRes>): ChatMessagesRes {
            return ChatMessagesRes(
                standardDate,
                messagesOfDate
            )
        }
    }
}
