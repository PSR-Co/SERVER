package com.psr.psr.global.jwt.exception

import com.fasterxml.jackson.databind.ObjectMapper
import com.psr.psr.global.dto.BaseResponse
import com.psr.psr.global.exception.BaseResponseCode
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class JwtAuthenticationEntryPoint(private val objectMapper: ObjectMapper) : AuthenticationEntryPoint{

    /**
     * 401 code error:
     * 클라이언트가 인증정보를 제대로 보내지 않은 경우 + 인증정보 부족한 경우 exception 발생
     */
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        response!!.contentType = "application/json;charset=UTF-8"
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        val exception = request?.getAttribute("exception")
        val writer = response.writer
        if (exception == null){
            writer.println(objectMapper.writeValueAsString(BaseResponse<Any>(BaseResponseCode.URL_NOT_FOUND)))
        }else {
            writer.println(objectMapper.writeValueAsString(exception))
        }
        writer.flush()
    }
}