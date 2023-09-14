package com.psr.psr.notification.dto

data class Notification(
    val title: String,
    val body: String
) {
    companion object {
        fun toDTO(title: String, body: String): Notification {
            return Notification(
                title = title,
                body = body
            )
        }
    }
}
