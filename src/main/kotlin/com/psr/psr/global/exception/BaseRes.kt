package com.psr.psr.global.exception

import org.springframework.http.HttpStatus

data class BaseRes(
    val status: Int,
    val message: String?
)
