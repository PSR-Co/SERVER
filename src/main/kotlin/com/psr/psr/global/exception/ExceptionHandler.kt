package com.psr.psr.global.exception


import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import com.psr.psr.global.dto.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException::class)
    protected fun handleHttpMessageNotReadableException(e:HttpMessageNotReadableException): ResponseEntity<*>{
        val msg = when (val causeException = e.cause) {
            is MissingKotlinParameterException -> {
                "${causeException.parameter.name}을(를) 입력해주세요."
            }
            else -> "요청 역직렬화 과정에서 에러가 발생했습니다."
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(BaseResponse.error(HttpStatus.BAD_REQUEST.value(), msg))
    }

}