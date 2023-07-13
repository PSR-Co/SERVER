package com.psr.psr.global.exception

import org.springframework.http.HttpStatus

enum class BaseResponseCode(status: HttpStatus, message: String) {
    SUCCESS(HttpStatus.OK, "요청에 성공했습니다.");

    public val status: HttpStatus = status
    public val message: String = message
}