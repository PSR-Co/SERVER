package com.psr.psr.global.config

import com.psr.psr.global.jwt.UserDetailsServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class WebSecurityConfig(
    private val userDetailsService: UserDetailsServiceImpl
){

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.
        csrf{c -> c.disable()}
        return http.build()
    }

}