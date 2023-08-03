package com.psr.psr.user.dto.eidReq

import jakarta.validation.constraints.NotBlank

data class BusinessListReq (
    @field:NotBlank
    val businesses: List<Business>
){

}