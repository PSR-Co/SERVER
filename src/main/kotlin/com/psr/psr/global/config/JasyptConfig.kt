package com.psr.psr.global.config

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JasyptConfig(
    @Value("\${jasypt.encryptor.password}") private val password: String
) {

    @Bean("jasyptSpringEncryptor")
    fun jasyptStringEncryptor(): StandardPBEStringEncryptor {
        val encryptor = StandardPBEStringEncryptor()
        encryptor.setAlgorithm("PBEWithMD5AndDES")
        encryptor.setPassword(password)
        return encryptor
    }
}