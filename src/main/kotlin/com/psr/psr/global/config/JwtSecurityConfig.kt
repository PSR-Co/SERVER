package com.psr.psr.global.config

import com.psr.psr.global.jwt.JwtFilter
import com.psr.psr.global.jwt.utils.JwtUtils
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class JwtSecurityConfig (private val jwtUtils: JwtUtils, private val redisTemplate: RedisTemplate<String, String>): SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> () {
    /**
     * jwtFilter를 SecurityFilter 앞에 추가
     */
    override fun configure(builder: HttpSecurity?) {
        val jwtFilter = JwtFilter(jwtUtils, redisTemplate)
        builder!!.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
    }
}