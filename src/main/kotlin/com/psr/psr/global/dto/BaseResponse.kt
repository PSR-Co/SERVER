package com.psr.psr.global.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.psr.psr.global.exception.BaseResponseCode

@JsonPropertyOrder("code", "message", "data")
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
        this.data = data
    }

    // fail
    constructor(status: BaseResponseCode) {
        this.code = status.status.value()
        this.message = status.message
    }
}