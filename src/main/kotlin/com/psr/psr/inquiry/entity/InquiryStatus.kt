package com.psr.psr.inquiry.entity

import com.psr.psr.global.exception.BaseException
import com.psr.psr.global.exception.BaseResponseCode

enum class InquiryStatus(val statusName: String) {
    PROGRESSING("진행중"),
    COMPLETED("완료");

    companion object {
        fun findByName(statusName: String): InquiryStatus{
            return InquiryStatus.values().find { it.statusName == statusName }
                ?: throw BaseException(BaseResponseCode.INVALID_INQUIRY_STATUS)
        }
    }
}