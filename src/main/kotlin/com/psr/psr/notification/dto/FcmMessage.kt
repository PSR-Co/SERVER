package com.psr.psr.notification.dto

data class FcmMessage (
    val validate_only: Boolean,
    val message: Message
)
