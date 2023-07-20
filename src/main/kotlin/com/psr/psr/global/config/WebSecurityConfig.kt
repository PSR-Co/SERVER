package com.psr.psr.global.config

import com.psr.psr.global.jwt.UserDetailsServiceImpl
import com.psr.psr.global.jwt.utils.JwtUtils
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class WebSecurityConfig(
    private val userDetailsService: UserDetailsServiceImpl,
    private val jwtUtils: JwtUtils
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { c -> c.disable() }
            .cors { c -> c.disable() }
            .sessionManagement{c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS)}
            // token 없이 사용이 가능한 api url 작성
            .authorizeHttpRequests { c ->
                c.requestMatchers("/global").permitAll()
                c.requestMatchers("/users/login").permitAll()
                c.requestMatchers("/users/signup").permitAll()
                c.anyRequest().authenticated()
            }
            .apply(JwtSecurityConfig(jwtUtils))

        return http.build()
    }

}