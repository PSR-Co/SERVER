package com.psr.psr.chat.dto.response

import com.querydsl.core.annotations.QueryProjection
import io.swagger.v3.oas.annotations.media.Schema

data class GetChatRoomsRes @QueryProjection constructor(
    @Schema(description = "채팅방 리스트")
    val noticeLists: List<ChatRoomRes>?
) {
    companion object {
        fun toDto(response: MutableList<ChatRoomRes>): GetChatRoomsRes? {
            return GetChatRoomsRes(response)
        }
    }
}