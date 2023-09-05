package com.psr.psr.user.dto.request

import jakarta.validation.constraints.NotNull

data class PostNotiReq(
    @field:NotNull
    val notification: Boolean
)
