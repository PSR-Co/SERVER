package com.psr.psr.global.entity

enum class ReportCategory(val value: String) {
    JUNK("스팸홍보/도배"),
    ABUSE("욕설/혐오/차별"),
    PORN("음란물/유해한 정보"),
    FRAUD("사기/불법정보"),
    NOT_FIT("게시글 성격에 부적합함")
}