package com.psr.psr.user.dto.eidReq

data class BusinessRes (
    val request_param: Business, // api 에 전달했던 request Body value
    val status: BusinessStatusRes ?= null //
)