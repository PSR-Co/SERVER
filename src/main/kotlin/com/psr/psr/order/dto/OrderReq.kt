package com.psr.psr.order.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class OrderReq (
    @field:NotNull(message = "상품ID를 입력해주세요.")
    val productId: Long,

    @field:NotBlank(message = "이름을 입력해주세요.")
    @field:Size(max = 100, message = "이름은 최대 100자입니다.")
    val ordererName: String,

    @field:Size(max = 255)
    var websiteUrl: String? = null,

    @field:NotBlank(message = "문의사항을 입력해주세요.")
    @field:Size(max = 250, message = "문의사항은 최대 250자입니다.")
    val inquiry: String,

    @field:NotBlank(message = "요청 상세설명을 입력해주세요.")
    @field:Size(max = 250, message = "요청 상세설명은 최대 250자입니다.")
    val description: String
)