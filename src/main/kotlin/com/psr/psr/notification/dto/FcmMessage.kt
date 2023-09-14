package com.psr.psr.notification.dto

data class FcmMessage (
    val validate_only: Boolean,
    val message: Message
) {
    companion object {
        fun makeMessage(targetToken: String, title: String, body: String, relatedId: Long, notiType: String): FcmMessage {
            return FcmMessage(
                validate_only = false,
                message = Message.toMessageDTO(targetToken, title, body, relatedId, notiType)
            )
        }
    }
}
