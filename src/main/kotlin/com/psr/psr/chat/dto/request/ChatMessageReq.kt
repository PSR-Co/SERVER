package com.psr.psr.chat.dto.request


import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

data class ChatMessageReq(

    @Schema(description = "메세지", example = "안녕하세요~")
    @field:NotBlank(message = "메세지를 입력해주세요.")
    val message: String

)

