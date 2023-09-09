package com.psr.psr.user.dto.phoneReq

import com.psr.psr.user.dto.request.ValidPhoneReq

data class SMSReq (
    val type: String?= "SMS",
    val contentType: String?="COMM",
    val countryCode: String?="82",
    val from: String,
    val content: String,
    val messages: List<MessageReq>
){
    companion object{
        fun toSMSReqDto(validPhoneReq: ValidPhoneReq, key: String, sendPhone: String) : SMSReq{
            val message = MessageReq(to = validPhoneReq.phone)
            return SMSReq(
                from = sendPhone,
                content = "[PSR] 인증번호는 [ $key ] 을 입력해주세요",
                messages = listOf(message) // 싱글톤 list = Collections.singletonList()
            )
        }
    }
}