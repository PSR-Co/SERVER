package com.psr.psr.user.dto.phoneReq

data class SMSReq (
    val type: String?= "SMS",
    val contentType: String?="COMM",
    val countryCode: String?="82",
    val from: String?="01020982316",
    val content: String,
    val messages: List<MessageReq>
)