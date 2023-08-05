package com.psr.psr.user.dto

import com.psr.psr.user.dto.eidReq.BusinessListReq
import com.psr.psr.user.dto.eidReq.Business
import jakarta.validation.constraints.NotBlank
import java.util.*


data class UserEidReq (
    @field:NotBlank
    val number: String,
    @field:NotBlank
    val companyDate: String,
    @field:NotBlank
    val companyName: String,
    @field:NotBlank
    val ownerName: String,
)