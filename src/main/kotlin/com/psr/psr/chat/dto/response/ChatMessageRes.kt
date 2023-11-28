package com.psr.psr.chat.dto.response

import com.fasterxml.jackson.annotation.JsonFormat
import com.psr.psr.chat.entity.ChatMessage
import com.psr.psr.user.entity.User
import com.querydsl.core.annotations.QueryProjection
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

data class ChatMessageRes @QueryProjection constructor(

    @Schema(description = "본인이 보낸 메시지 여부", example = "false")
    val isSend: Boolean,

    @Schema(description = "메시지", example = "안녕하세요!")
    val message: String,

    @Schema(description = "메시지 보낸 시간", example = "11:30")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:MM")
    val date: LocalDateTime,

    ) {
    companion object {
        fun toDto(user: User, chatMessage: ChatMessage): ChatMessageRes {
            val isSend: Boolean = chatMessage.senderUser == user
            return ChatMessageRes(
                isSend,
                chatMessage.message,
                chatMessage.createdAt
            )

        }
    }
}
