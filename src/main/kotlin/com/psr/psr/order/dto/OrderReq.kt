package com.psr.psr.order.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class OrderReq (
    @Schema(description = "상품 Id", example = "1")
    val productId: Long?,

    @Schema(description = "요청자 이름", example = "박서연")
    @field:NotBlank(message = "이름을 입력해주세요.")
    @field:Size(max = 100, message = "이름은 최대 100자입니다.")
    val ordererName: String,

    @Schema(description = "사업자/쇼핑몰 URL", example = "www.123.com")
    @field:Size(max = 255)
    var websiteUrl: String? = null,

    @Schema(description = "문의사항", example = "문의사항 내용")
    @field:NotBlank(message = "문의사항을 입력해주세요.")
    @field:Size(max = 250, message = "문의사항은 최대 250자입니다.")
    val inquiry: String,

    @Schema(description = "요청 상세 설명", example = "오늘 해주세요")
    @field:NotBlank(message = "요청 상세설명을 입력해주세요.")
    @field:Size(max = 250, message = "요청 상세설명은 최대 250자입니다.")
    val description: String
)