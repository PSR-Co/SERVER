package com.psr.psr.review.dto

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class ReviewReportReq(
    @field:NotNull(message = "별점을 입력해주세요.")
    @field:Min(value = 1, message = "별점은 1~5점이여야 합니다.")
    @field:Max(value = 5, message = "별점은 1~5점이여야 합니다.")
    val rating: Int,

    @field:NotBlank(message = "내용을 입력해주세요.")
    @field:Size(max = 250, message = "내용은 최대 250자입니다.")
    val content: String,

    @field:Size(min = 1, max = 5, message = "imgKey는 null 또는 1~5개이어야 합니다.")
    val imgList: List<String>?
)
