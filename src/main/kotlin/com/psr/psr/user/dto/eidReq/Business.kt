package com.psr.psr.user.dto.eidReq

import jakarta.validation.constraints.NotBlank

data class Business (
    @field:NotBlank
    val b_no: String, // 사업자 번호
    @field:NotBlank
    val start_dt: String, // 개업 일자
    @field:NotBlank
    val p_nm: String, // 사업자 대표
    @field:NotBlank
    val b_nm: String // 회사 이름
){
}