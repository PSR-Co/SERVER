package com.psr.psr.global.entity

import com.psr.psr.global.exception.BaseException
import com.psr.psr.global.exception.BaseResponseCode

enum class ReportCategory(val value: String) {
    JUNK("스팸홍보/도배"),
    ABUSE("욕설/혐오/차별"),
    PORN("음란물/유해한 정보"),
    FRAUD("사기/불법정보"),
    NOT_FIT("게시글 성격에 부적합함");

    companion object {
        fun findByName(name: String): ReportCategory {
            return ReportCategory.values().find { it.name == name }
                ?: throw BaseException(BaseResponseCode.INVALID_REPORT_CATEGORY)
        }
    }
}