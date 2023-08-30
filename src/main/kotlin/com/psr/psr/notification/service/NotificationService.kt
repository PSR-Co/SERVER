package com.psr.psr.notification.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.auth.oauth2.GoogleCredentials
import com.psr.psr.global.Constant.NotiSentence.NotiSentence.NEW_ORDER_SENTENCE
import com.psr.psr.global.Constant.NotiSentence.NotiSentence.TWO_MONTH_ORDER_SENTENCE
import com.psr.psr.notification.dto.FcmMessage
import com.psr.psr.notification.dto.NotiAssembler
import com.psr.psr.notification.dto.NotificationListRes
import com.psr.psr.notification.entity.NotificationType
import com.psr.psr.notification.repository.NotificationRepository
import com.psr.psr.order.entity.OrderStatus
import com.psr.psr.user.entity.User
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ClassPathResource
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service

@Service
class NotificationService(
    private val notificationRepository: NotificationRepository,
    private val notiAssembler: NotiAssembler,
    @Value("\${firebase.sendUrl}") private val sendUrl: String,
    private val objectMapper: ObjectMapper
) {
    // 알림 목록 조회
    fun getNotiList(user: User, pageable: Pageable): Page<NotificationListRes> {
        return notificationRepository.findNotificationByUserGroupByDate(user, pageable)
    }

    // 새로운 요청 알림
    fun sendNewOrderNoti(productName: String, orderReceiver: User, ordererName: String, orderId: Long) {
        val messageBody = ordererName + NEW_ORDER_SENTENCE
        notificationRepository.save(notiAssembler.toEntity(
            orderReceiver,
            productName,
            messageBody,
            orderId,
            NotificationType.NEW_ORDER
        ))

        if (isPushNotiAvailable(orderReceiver)) {
            val message: FcmMessage = notiAssembler.makeMessage(
                orderReceiver.deviceToken!!,
                productName,
                messageBody,
                orderId,
                NotificationType.NEW_ORDER.name
            )
            sendMessage(objectMapper.writeValueAsString(message))
        }
    }

    // 요청 상태 변경 알림
    fun sendChangeOrderStatusNoti(productName: String, orderer: User, orderStatus: OrderStatus, orderId: Long) {
        val messageBody = orderStatus.notiSentence!!
        notificationRepository.save(notiAssembler.toEntity(
            orderer,
            productName,
            messageBody,
            orderId,
            NotificationType.CHANGED_ORDER_STATUS
        ))

        if (isPushNotiAvailable(orderer)) {
            val message: FcmMessage = notiAssembler.makeMessage(
                orderer.deviceToken!!,
                productName,
                messageBody,
                orderId,
                NotificationType.CHANGED_ORDER_STATUS.name
            )
            sendMessage(objectMapper.writeValueAsString(message))
        }
    }

    // 2달 뒤 요청상태 입력 요망 알림
    fun send2MonthOrderNoti(productName: String, orderReceiver: User, ordererName: String, orderId: Long) {
        val messageBody = ordererName + TWO_MONTH_ORDER_SENTENCE
        notificationRepository.save(notiAssembler.toEntity(
            orderReceiver,
            productName,
            messageBody,
            orderId,
            NotificationType.TWO_MONTH_ORDER
        ))

        if (isPushNotiAvailable(orderReceiver)) {
            val message: FcmMessage = notiAssembler.makeMessage(
                orderReceiver.deviceToken!!,
                productName,
                messageBody,
                orderId,
                NotificationType.TWO_MONTH_ORDER.name
            )
            sendMessage(objectMapper.writeValueAsString(message))
        }
    }

    // 채팅 알림
    fun sendChatNoti(chatReceiver: User, senderNickname: String, chatMessage: String, chatRoomId: Long) {
        if (isPushNotiAvailable(chatReceiver)) {
            val message: FcmMessage = notiAssembler.makeMessage(
                chatReceiver.deviceToken!!,
                senderNickname,
                chatMessage,
                chatRoomId,
                NotificationType.CHAT.name
            )
            sendMessage(objectMapper.writeValueAsString(message))
        }
    }

    // 알림 수신 상태 체크
    fun isPushNotiAvailable(user: User): Boolean {
        return user.deviceToken != null && user.notification
    }

    // firebase accessToken 발급
    private fun getAccessToken(): String? {
        val firebaseConfigPath = "firebase-service-key.json"
        val googleCredentials = GoogleCredentials
            .fromStream(ClassPathResource(firebaseConfigPath).inputStream)
            .createScoped(listOf("https://www.googleapis.com/auth/cloud-platform"))
        googleCredentials.refreshIfExpired()
        return googleCredentials.accessToken.tokenValue
    }

    // 메세지 전송
    private fun sendMessage(message: String): Response {
        val client = OkHttpClient()
        val requestBody: RequestBody = message.toRequestBody("application/json; charset=utf-8".toMediaType())
        val request: Request = Request.Builder()
            .url(sendUrl)
            .post(requestBody)
            .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
            .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
            .build()
        return client.newCall(request).execute()
    }
}