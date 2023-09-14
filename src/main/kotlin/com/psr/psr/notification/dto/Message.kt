package com.psr.psr.notification.dto

data class Message(
    val notification: Notification,
    val token: String,
    val data: Data
) {
    companion object {
        fun toMessageDTO(targetToken: String, title: String, body: String, relatedId: Long, notiType: String): Message {
            return Message(
                notification = Notification.toDTO(title, body),
                token = targetToken,
                data = Data.toDTO(relatedId, notiType)
            )
        }
    }
}
