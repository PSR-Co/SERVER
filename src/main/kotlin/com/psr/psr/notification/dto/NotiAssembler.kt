package com.psr.psr.notification.dto

import com.psr.psr.notification.entity.NotificationType
import com.psr.psr.notification.entity.PushNotification
import com.psr.psr.user.entity.User
import org.springframework.stereotype.Component


@Component
class NotiAssembler {
    fun toEntity(receiver: User, title: String, content: String, relatedId: Long, type: NotificationType): PushNotification {
        return PushNotification(
            user = receiver,
            title = title,
            content = content,
            relatedId = relatedId,
            type = type
        )
    }
    fun toMessageDTO(targetToken: String, title: String, body: String, relatedId: Long, notiType: String): Message {
        return Message(
            notification = toNotificationDTO(title, body),
            token = targetToken,
            data = toDataDTO(relatedId, notiType)
        )
    }

    fun toNotificationDTO(title: String, body: String): Notification {
        return Notification(
            title = title,
            body = body
        )
    }

    fun toDataDTO(relatedId: Long, notiType: String): Data {
        return Data(
            relatedId = relatedId,
            notiType = notiType
        )
    }

    fun makeMessage(targetToken: String, title: String, body: String, relatedId: Long, notiType: String): FcmMessage {
        return FcmMessage(
            validate_only = false,
            message = toMessageDTO(targetToken, title, body, relatedId, notiType)
        )
    }
}