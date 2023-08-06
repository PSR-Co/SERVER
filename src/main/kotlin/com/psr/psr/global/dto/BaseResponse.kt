package com.psr.psr.global.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.psr.psr.global.exception.BaseResponseCode
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.RequiredArgsConstructor

@JsonPropertyOrder("code", "message", "data")
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
class BaseResponse<T> {
    @JsonProperty("code")
    private val code: Int

    @JsonProperty("message")
    private val message: String

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("data")
    private var data: T? = null

    // success
    constructor(result: T) {
        this.code = BaseResponseCode.SUCCESS.status.value()
        this.message = BaseResponseCode.SUCCESS.message
        this.data = result
    }

    // fail
    constructor(code: Int, message: String) {
        this.code = code
        this.message = message
    }

    companion object {
        fun error(code: Int, message: String): BaseResponse<*> {
            return BaseResponse<Any>(code, message)
        }
    }
}