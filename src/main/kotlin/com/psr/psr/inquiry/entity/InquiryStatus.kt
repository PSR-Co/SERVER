package com.psr.psr.inquiry.entity

import com.psr.psr.global.exception.BaseException
import com.psr.psr.global.exception.BaseResponseCode

enum class InquiryStatus(val num: Int, val value: String) {
    PROGRESSING(0, "진행중"),
    COMPLETED(1, "완료");

    companion object {
        fun findByNum(num: Int): InquiryStatus{
            return InquiryStatus.values().find { it.num == num }
                ?: throw BaseException(BaseResponseCode.INVALID_INQUIRY_STATUS)
        }
    }
}