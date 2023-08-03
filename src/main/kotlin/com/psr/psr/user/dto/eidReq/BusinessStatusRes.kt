package com.psr.psr.user.dto.eidReq

data class BusinessStatusRes (
    val b_no: String, // 사업자 등록번호
    val b_stt: String, // 납세자 상태
    val b_stt_cd: String, // 납세자 코드
    val tax_type: String, // 과세 유형 메시지
    val tax_type_cd: String, // 과세 유형 코드
    val end_dt: String, // 폐업일 (YYYYMMDD)
    val utcc_yn: String, // 단위 과세 전환 폐업 여부 (Y, N)
    val tax_type_change_dt: String, // 최근 과세 유형 전환 일자
    val invoice_apply_dt: String, // 세금 계산서 적용 일자
    val rbf_tax_type: String, // 직전 과세 유형 메시지
    val rbf_tax_type_cd: String // 직전 과세 유형 메시지
)