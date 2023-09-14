package com.psr.psr.notification.dto

data class Data(
    val relatedId: Long,
    val notiType: String
) {
    companion object {
        fun toDTO(relatedId: Long, notiType: String): Data {
            return Data(
                relatedId = relatedId,
                notiType = notiType
            )
        }
    }
}
