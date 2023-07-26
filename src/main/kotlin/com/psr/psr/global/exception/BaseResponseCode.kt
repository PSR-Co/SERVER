package com.psr.psr.global.exception

import org.springframework.http.HttpStatus

enum class BaseResponseCode(status: HttpStatus, message: String) {
    SUCCESS(HttpStatus.OK, "요청에 성공했습니다."),

    // token
    NULL_TOKEN(HttpStatus.UNAUTHORIZED, "토큰 값을 입력해주세요."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰 값입니다."),
    UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED, "잘못된 형식의 토큰 값입니다."),
    MALFORMED_TOKEN(HttpStatus.UNAUTHORIZED, "잘못된 구조의 토큰 값입니다."),
    EXPIRED_TOKEN(HttpStatus.FORBIDDEN, "만료된 토큰 값입니다."),

    // user
    INVALID_EMAIL(HttpStatus.BAD_REQUEST, "올바르지 않은 이메일 형식입니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "올바르지 않은 비밀번호 형식입니다."),
    INVALID_PHONE(HttpStatus.BAD_REQUEST, "올바르지 않은 휴대폰 형식입니다."),
    EXISTS_PHONE(HttpStatus.BAD_REQUEST, "이미 가입되어 있는 휴대폰 번호입니다."),
    EXISTS_EMAIL(HttpStatus.BAD_REQUEST, "이미 가입되어 있는 이메일입니다."),
    EXISTS_NICKNAME(HttpStatus.BAD_REQUEST, "이미 가입되어 있는 닉네임입니다."),

    // User - type
    INVALID_USER_TYPE_NAME(HttpStatus.BAD_REQUEST, "올바르지 않은 사용자 역할입니다.");

    val status: HttpStatus = status
    val message: String = message
}