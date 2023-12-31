package com.psr.psr.inquiry.entity

import com.psr.psr.global.entity.BaseEntity
import com.psr.psr.inquiry.dto.InquiryReq
import com.psr.psr.user.entity.User
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.jetbrains.annotations.NotNull

@Entity
@SQLDelete(sql = "UPDATE inquiry SET status = 'inactive', updated_at = current_timestamp WHERE id = ?")
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
    fun registerAnswer(answer: String){
        this.answer = answer
        this.inquiryStatus = InquiryStatus.COMPLETED
    }

    companion object {
        fun toEntity(user: User, inquiryReq: InquiryReq): Inquiry {
            return Inquiry(
                user = user,
                title = inquiryReq.title,
                content = inquiryReq.content
            )
        }
    }
}
