package com.psr.psr.global.config

import com.psr.psr.global.jwt.exception.JwtAccessDeniedHandler
import com.psr.psr.global.jwt.exception.JwtAuthenticationEntryPoint
import com.psr.psr.global.jwt.utils.JwtUtils
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@EnableWebSecurity
class WebSecurityConfig(
    private val jwtUtils: JwtUtils,
    private val redisTemplate: RedisTemplate<String, String>,
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    private val jwtAccessDeniedHandler: JwtAccessDeniedHandler
) {
    @Bean
    fun PasswordEncoder() : PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { c -> c.disable() }
            .cors { c -> c.disable() }
            .exceptionHandling {
                e ->
                e.authenticationEntryPoint(jwtAuthenticationEntryPoint)
                e.accessDeniedHandler(jwtAccessDeniedHandler)

            }
            .sessionManagement{c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS)}
            // token 없이 사용이 가능한 api url 작성
            .authorizeHttpRequests { c ->
                c.requestMatchers("/global").permitAll()
                c.requestMatchers("/inquiries").permitAll()
                c.requestMatchers("/users/login").permitAll()
                c.requestMatchers("/users/signup").permitAll()
                c.requestMatchers("/users/nickname").permitAll()
                c.requestMatchers("/users/eid").permitAll()
                c.requestMatchers("/users/reissue").permitAll()
                c.anyRequest().authenticated()
            }
            .apply(JwtSecurityConfig(jwtUtils, redisTemplate))

        return http.build()
    }

}