package com.psr.psr.global.exception

import com.psr.psr.global.dto.BaseResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(BaseException::class)
    protected fun handleBaseException(e: BaseException): ResponseEntity<BaseRes>{
        return ResponseEntity.status(e.baseResponseCode.status)
            .body(BaseRes(e.baseResponseCode.status.value(), e.baseResponseCode.message))
    }
}