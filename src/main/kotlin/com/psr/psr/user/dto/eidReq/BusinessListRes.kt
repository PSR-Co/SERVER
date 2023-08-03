package com.psr.psr.user.dto.eidReq

data class BusinessListRes (
    val request_cnt: Int, // 조회 요청 수
    val valid_cnt: Int ? = null, // 검증 valid 수
    val status_code: String, // API 상태 코드
    val data: List<BusinessRes> // data
)