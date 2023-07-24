package com.psr.psr.global.jwt.exception

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

@Component
class JwtAccessDeniedHandler(objectMapper: ObjectMapper) : AccessDeniedHandler {

    /**
     * 403 code error
     * 서버가 요청을 이해했지만, 권한이 없이 요청을 거부
     */
    override fun handle(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        accessDeniedException: AccessDeniedException?
    ) {
        response!!.status = HttpServletResponse.SC_FORBIDDEN
    }
}