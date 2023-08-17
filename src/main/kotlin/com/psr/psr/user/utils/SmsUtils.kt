package com.psr.psr.user.utils

import com.psr.psr.global.exception.BaseException
import com.psr.psr.global.exception.BaseResponseCode
import com.psr.psr.user.service.RedisService
import org.springframework.stereotype.Component
import org.springframework.util.ObjectUtils
import java.time.Duration

@Component
class SmsUtils (
    private val redisService: RedisService
){
    private final val SMS_KEY_EXPIRE_TIME: Long = 1000L * 60 * 5 // 5분

    /**
     * 휴대폰 smsKey 만료시간
     */
    fun createSmsKey(phone: String, smsKey: String){
        // 재발급을 받은 경우 기존 인증코드 삭제
        if (redisService.getValue(phone) != null) redisService.deleteValue(phone)
        // 인증코드 생성
        redisService.setValue(phone, smsKey, Duration.ofMillis(SMS_KEY_EXPIRE_TIME))
    }

    /**
     * 휴대폰 인증코드 정보 불러오기
     */
    fun getSmsKey(phone: String) : String {
        val key = redisService.getValue(phone)
        // sms key 가 없거나 만료된 경우 예외처리
        if(ObjectUtils.isEmpty(key) || key == null) throw BaseException(BaseResponseCode.BLACKLIST_PHONE)
        return key
    }
}