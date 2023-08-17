package com.psr.psr.user.service

import io.netty.util.Timeout
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import java.util.concurrent.TimeUnit

@Service
@Transactional(readOnly = true)
class RedisService (
    private val redisTemplate: RedisTemplate<String, String>
){

    fun setValue(key: String, value: String, time: Duration){
        redisTemplate.opsForValue().set(key, value, time)
    }

    fun setValue(key: String, value: String, exp: Long, time: TimeUnit){
        redisTemplate.opsForValue().set(key, value, exp, time)
    }

    fun getValue(key : String) : String? {
        return redisTemplate.opsForValue().get(key)
    }

    @Transactional
    fun deleteValue(key : String) {
        redisTemplate.delete(key)
    }
}