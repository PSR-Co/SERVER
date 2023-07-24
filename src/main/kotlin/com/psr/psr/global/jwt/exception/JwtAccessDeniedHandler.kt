package com.psr.psr.global.jwt.exception

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

@Component
class JwtAccessDeniedHandler(private val objectMapper: ObjectMapper) : AccessDeniedHandler {

    /**
     * 403 code error
     * 서버가 요청을 이해했지만, 권한이 없이 요청을 거부
     */
    override fun handle(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        accessDeniedException: AccessDeniedException?
    ) {
        response!!.contentType = "application/json;charset=UTF-8"
        response.status = HttpServletResponse.SC_FORBIDDEN
        val exception = request?.getAttribute("exception")
        val writer = response.writer
        writer.println(objectMapper.writeValueAsString(exception))
        writer.flush()
    }
}