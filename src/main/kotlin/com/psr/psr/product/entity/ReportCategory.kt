package com.psr.psr.product.entity

enum class ReportCategory(val value: String) {
    JUNK("홍보/도배"),
    PORN("음란물/유해한 정보"),
    POOR_CONTENT("내용이 부실함"),
    NOT_FIT("게시글 성격에 부적합함")
}