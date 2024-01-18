package com.psr.psr.chat.dto.response

import io.swagger.v3.oas.annotations.media.Schema

data class GetChatMessagesRes(
    @Schema(description = "채팅 시작된 게시글 이름", example = "목도리")
    val productName: String? = null,

    @Schema(description = "채팅 메시지 리스트")
    val chatMessages: List<ChatMessageRes>?
) {
    companion object {
        fun toDto(name: String, chatMessages: List<ChatMessageRes>?): GetChatMessagesRes {
            return GetChatMessagesRes(
                name,
                chatMessages
            )
        }

    }
}
