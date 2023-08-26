package com.psr.psr.notification.dto

data class Message(
    val notification: Notification,
    val token: String,
    val data: Data
)
