package com.psr.psr.user.dto.request

import com.psr.psr.user.dto.eidReq.BusinessListReq
import com.psr.psr.user.dto.eidReq.Business
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import java.util.*


data class UserEidReq (
    @field:NotBlank
    @Schema(type = "String", description = "사업자 등록번호", example = "123455667", required = true)
    val number: String,
    @field:NotBlank
    @Schema(type = "String", description = "사업자 개업 날짜 (yyyyMMdd)", example = "길동이반찬가게", required = true)
    val companyDate: String,
    @field:NotBlank
    @Schema(type = "String", description = "상호명", example = "길동이반찬가게", required = true)
    val companyName: String,
    @field:NotBlank
    @Schema(type = "String", description = "대표자명", example = "홍길동", required = true)
    val ownerName: String,
)