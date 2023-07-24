package com.psr.psr.global.jwt

import com.psr.psr.global.Constant.JWT.AUTHORIZATION_HEADER
import com.psr.psr.global.Constant.JWT.BEARER_PREFIX
import com.psr.psr.global.dto.BaseResponse
import com.psr.psr.global.exception.BaseException
import com.psr.psr.global.jwt.utils.JwtUtils
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter

class JwtFilter(private val jwtUtils: JwtUtils) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try{
            val token = resolveToken(request)
            if(StringUtils.hasText(token) && jwtUtils.validateToken(token)){
                val authentication = jwtUtils.getAuthentication(token!!)
                SecurityContextHolder.getContext().authentication = authentication
            }
        } catch (e: BaseException) {
            request.setAttribute("exception", BaseResponse<Any>(e.baseResponseCode))
        }
        filterChain.doFilter(request, response);

    }

    /**
     * header 에서 값을 꺼내는 method
     */
    private fun resolveToken(request: HttpServletRequest): String? {
        val token = request.getHeader(AUTHORIZATION_HEADER)

        return if (StringUtils.hasText(token) && token.startsWith(BEARER_PREFIX)) {
            token.substring(BEARER_PREFIX.length)
        } else null
    }
}