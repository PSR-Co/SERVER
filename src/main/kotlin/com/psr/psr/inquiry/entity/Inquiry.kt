package com.psr.psr.inquiry.entity

import com.psr.psr.global.entity.BaseEntity
import com.psr.psr.inquiry.dto.InquiryListRes
import com.psr.psr.inquiry.dto.InquiryRes
import com.psr.psr.user.entity.User
import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Entity
data class Inquiry(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    var user: User,

    @NotNull
    @Column(length = 100)
    var title: String?,

    @NotNull
    var content: String?,

    @NotNull
    @Enumerated(EnumType.STRING)
    var inquiryStatus: InquiryStatus? = InquiryStatus.PROGRESSING,

    var answer: String? = null

): BaseEntity(){
    fun toListDto(): InquiryListRes {
        return InquiryListRes(
            inquiryId = id!!,
            title = title!!
        )
    }
    
    fun toDto(): InquiryRes {
        return InquiryRes(
            inquiryId = id!!,
            title = title!!,
            content = content!!,
            answer = answer
        )
    }
}
