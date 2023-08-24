package com.psr.psr.notification.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.auth.oauth2.GoogleCredentials
import com.psr.psr.notification.dto.NotiAssembler
import com.psr.psr.notification.dto.NotificationListRes
import com.psr.psr.notification.repository.NotificationRepository
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