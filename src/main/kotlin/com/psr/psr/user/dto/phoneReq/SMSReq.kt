package com.psr.psr.user.dto.phoneReq

data class SMSReq (
    val type: String?= "SMS",
    val contentType: String?="COMM",
    val countryCode: String?="82",
    val from: String,
    val content: String,
    val messages: List<MessageReq>
){
    companion object{
        fun toSMSReqDto(phone: String, key: String, sendPhone: String) : SMSReq{
            val message = MessageReq(to = phone)
            return SMSReq(
                from = sendPhone,
                content = "[PSR] 인증번호는 [ $key ] 을 입력해주세요",
                messages = listOf(message) // 싱글톤 list = Collections.singletonList()
            )
        }
    }
}