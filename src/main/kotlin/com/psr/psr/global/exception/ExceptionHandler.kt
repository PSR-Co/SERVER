package com.psr.psr.global.exception


import com.psr.psr.global.dto.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.*


@RestControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(BaseException::class)
    protected fun handleBaseException(e: BaseException): ResponseEntity<*>{
        return ResponseEntity.status(e.baseResponseCode.status)
            .body(BaseResponse.error(e.baseResponseCode.status.value(), e.baseResponseCode.message))
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    protected fun handleMethodArgumentNotValidException(e:MethodArgumentNotValidException): ResponseEntity<*>{
        val fieldError = Objects.requireNonNull(e.fieldError)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(BaseResponse.error(HttpStatus.BAD_REQUEST.value(), String.format("%s", fieldError?.defaultMessage)))
    }

}