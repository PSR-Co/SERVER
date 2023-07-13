package com.psr.psr.global.controller

import com.psr.psr.global.dto.BaseResponse
import com.psr.psr.global.exception.BaseResponseCode.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/global")
class globalController {

    @GetMapping
    fun index(): BaseResponse<Any> {
        val hello = "Hello World!"
        return BaseResponse(SUCCESS)
    }
}