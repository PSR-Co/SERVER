package com.psr.psr.global.jwt.utils

import com.psr.psr.global.Constant.JWT.JWT.AUTHORIZATION_HEADER
import com.psr.psr.global.Constant.JWT.JWT.BEARER_PREFIX
import com.psr.psr.global.exception.BaseException
import com.psr.psr.global.exception.BaseResponseCode
import com.psr.psr.global.jwt.UserDetailsServiceImpl
import com.psr.psr.global.jwt.dto.TokenRes
import com.psr.psr.user.entity.Type
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SecurityException
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.security.Key
import java.time.Duration
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.stream.Collectors


@Component
class JwtUtils(
    private val userDetailsService: UserDetailsServiceImpl,
    @Value("\${jwt.secret}") private val secret: String,
    private val redisTemplate: RedisTemplate<String, String>
) {
    val logger = KotlinLogging.logger {}

    private final val ACCESS_TOKEN_EXPIRE_TIME: Long = 1000L * 60 * 30 // 30 분
    private final val REFRESH_TOKEN_EXPIRE_TIME: Long = 1000L * 60 * 60 * 24 * 7 // 일주일

    private val keyBytes = Decoders.BASE64.decode(secret)
    val key: Key = Keys.hmacShaKeyFor(keyBytes)

    /**
     * 토큰 생성
     */
    fun createToken(authentication: Authentication, type: Type): TokenRes {
        val authorities = authentication.authorities.stream()
            .map { obj: GrantedAuthority -> obj.authority }
            .collect(Collectors.joining(","))

        val now = Date().time
        val accessToken: String = Jwts.builder()
            .setSubject(authentication.name)
            .claim(AUTHORIZATION_HEADER, authorities)
            .setExpiration(Date(now + ACCESS_TOKEN_EXPIRE_TIME))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()

        val refreshToken: String = Jwts.builder()
            .setExpiration(Date(now + REFRESH_TOKEN_EXPIRE_TIME))
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()
        redisTemplate.opsForValue().set(authentication.name, refreshToken, Duration.ofMillis(REFRESH_TOKEN_EXPIRE_TIME))

        return TokenRes(BEARER_PREFIX + accessToken, BEARER_PREFIX + refreshToken, type.value)
    }

    /**
     * 토큰 유효성 검사
     */
    fun validateToken(token: String?): Boolean {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            return true
        } catch (e: SecurityException) {
            throw BaseException(BaseResponseCode.INVALID_TOKEN)
        } catch (e: MalformedJwtException) {
            throw BaseException(BaseResponseCode.MALFORMED_TOKEN)
        } catch (e: ExpiredJwtException) {
            throw BaseException(BaseResponseCode.EXPIRED_TOKEN)
        } catch (e: UnsupportedJwtException) {
            throw BaseException(BaseResponseCode.UNSUPPORTED_TOKEN)
        } catch (e: IllegalArgumentException) {
            throw BaseException(BaseResponseCode.NULL_TOKEN)
        }
    }


    /**
     * 토큰 복호화
     */
    fun getAuthentication(accessToken: String): Authentication {
        val userId = getUserIdFromJWT(accessToken)
        val userDetails: UserDetails = userDetailsService.loadUserById(userId)
        return UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
    }

    fun getUserIdFromJWT(token: String): Long {
        return parseClaims(token).subject.toLong()
    }

    private fun parseClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
    }

    /**
     * 토큰 만료
     */
    fun expireToken(token: String, status: String){
        val accessToken = token.replace(BEARER_PREFIX, "")
        val expiration = getExpiration(accessToken)
        redisTemplate.opsForValue().set(accessToken, status, expiration, TimeUnit.MILLISECONDS)

    }

    /**
     * Token 남은 시간 return
     */
    private fun getExpiration(token: String): Long {
        val expiration = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body.expiration
        val now = Date().time
        return (expiration.time - now)
    }

    /**
     * refresh token 삭제
     */
    fun deleteRefreshToken(userId: Long) {
        if(redisTemplate.opsForValue().get(userId.toString()) != null) redisTemplate.delete(userId.toString())
    }



}