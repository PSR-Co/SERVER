package com.psr.psr.chat.dto.response

import com.fasterxml.jackson.annotation.JsonFormat
import com.psr.psr.chat.entity.ChatMessage
import com.psr.psr.chat.entity.ChatRoom
import com.psr.psr.user.entity.User
import com.querydsl.core.annotations.QueryProjection
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

data class ChatRoomRes @QueryProjection constructor(
    @Schema(description = "채팅방 id", example = "1")
    val chatRoomId: Long,

    @Schema(description = "프로필 이미지", example = "imgUrl")
    val profileImgUrl: String? = null,

    @Schema(description = "닉네임", example = "마리")
    val nickname: String,

    @Schema(description = "최근 메시지", example = "안녕하세요!")
    val message: String? = null,

    @Schema(description = "최근 메시지 날짜", example = "11.30")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM.dd")
    val date: LocalDateTime? = null,

    @Schema(description = "본인이 메시지 읽음 여부", example = "false")
    val isRead: Boolean,

    @Schema(description = "읽지 않은 메시지 수 (없으면 0)", example = "3")
    val numOfUnread: Int? = null

) {
    companion object {
        fun toDto(chatRoom: ChatRoom, chatMessage: ChatMessage, partner: User, numOfUnread: Int): ChatRoomRes {
            val isRead: Boolean =
                if (chatMessage.senderUser == partner) chatMessage.isRead
                else true

            return ChatRoomRes(
                chatRoom.id!!,
                partner.imgUrl,
                partner.nickname,
                chatMessage.message,
                chatMessage.createdAt,
                isRead,
                numOfUnread
            )

        }
    }
}