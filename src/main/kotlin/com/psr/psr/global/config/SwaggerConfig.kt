package com.psr.psr.global.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun openAPI(): OpenAPI {
        val info = Info().title("PSR API")
            .description("PSR API 명세서입니다.")
            .version("v1.0.0")

        // Security 스키마 설정
        val bearerAuth = SecurityScheme()
            .name("Authorization")
            .type(SecurityScheme.Type.HTTP)
            .`in`(SecurityScheme.In.HEADER)
            .scheme("bearer")
            .bearerFormat("JWT")

        // Security 요청 설정
        val addSecurityItem = SecurityRequirement()
        addSecurityItem.addList("JWT")
        return OpenAPI()
            .components(Components().addSecuritySchemes("Bearer", bearerAuth))
            .addSecurityItem(addSecurityItem)
            .info(info)
    }
}