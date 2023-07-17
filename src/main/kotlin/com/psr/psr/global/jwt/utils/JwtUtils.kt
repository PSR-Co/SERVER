package com.psr.psr.global.jwt.utils

import com.psr.psr.global.Constant.JWT.BEARER_PREFIX
import com.psr.psr.global.jwt.UserDetailsServiceImpl
import com.psr.psr.global.jwt.dto.TokenRes
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SecurityException
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*
import java.util.stream.Collectors


@Component
class JwtUtils(
    private val userDetailsService: UserDetailsServiceImpl,
    @Value("\${jwt.secret}") private val secret: String
) {
    val logger = KotlinLogging.logger {}
    private final val AUTHORITIES_KEY: String = "auth"
    private final val BEARER_TYPE: String = "bearer"

    // todo: 암호화시켜 yaml 파일에 넣어두기
    private final val ACCESS_TOKEN_EXPIRE_TIME: Long = 1000L * 60 * 30 // 30 분
    private final val REFRESH_TOKEN_EXPIRE_TIME: Long = 1000L * 60 * 60 * 24 * 7 // 일주일

    private val keyBytes = Decoders.BASE64.decode(secret)
    val key: Key = Keys.hmacShaKeyFor(keyBytes)

    /**
     * 토큰 생성
     */
    fun createToken(authentication: Authentication): TokenRes {
        val authorities = authentication.authorities.stream()
            .map { obj: GrantedAuthority -> obj.authority }
            .collect(Collectors.joining(","))

        val now = Date().time
        val accessToken: String = Jwts.builder()
            .setSubject(authentication.name)
            .claim(AUTHORITIES_KEY, authorities)
            .setExpiration(Date(now + ACCESS_TOKEN_EXPIRE_TIME))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()

        val refreshToken: String = Jwts.builder()
            .setExpiration(Date(now + REFRESH_TOKEN_EXPIRE_TIME))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()

        return TokenRes(BEARER_PREFIX + accessToken, BEARER_PREFIX + refreshToken)
    }

    /**
     * 토큰 유효성 검사
     */
    fun validateToken(token: String?): Boolean {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            return true
        } catch (e: SecurityException) {
            logger.info("Invalid JWT Token", e)
        } catch (e: MalformedJwtException) {
            logger.info("Invalid JWT Token", e)
        } catch (e: ExpiredJwtException) {
            logger.info("Expired JWT Token", e)
        } catch (e: UnsupportedJwtException) {
            logger.info("Unsupported JWT Token", e)
        } catch (e: IllegalArgumentException) {
            logger.info("JWT claims string is empty.", e)
        }
        return false
    }


    /**
     * 토큰 복호화
     */
    fun getAuthentication(accessToken: String): Authentication {
        val claims = parseClaims(accessToken)
        // todo: 에러 추가
        if (claims.get(AUTHORITIES_KEY) == null) logger.info("토큰 복호화 error")
        val userDetails: UserDetails = userDetailsService.loadUserByUsername(claims.subject)
        return UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
    }

    private fun parseClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
    }
}