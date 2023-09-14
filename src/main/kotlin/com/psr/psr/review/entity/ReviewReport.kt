package com.psr.psr.review.entity

import com.psr.psr.global.entity.BaseEntity
import com.psr.psr.global.entity.ReportCategory
import com.psr.psr.user.entity.User
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.jetbrains.annotations.NotNull

@Entity
@SQLDelete(sql = "UPDATE review_report SET status = 'inactive', updated_at = current_timestamp WHERE id = ?")
data class ReviewReport(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(nullable = false, name = "review_id")
    var review: Review,

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    var user: User,

    @NotNull
    @Enumerated(EnumType.STRING)
    var category: ReportCategory

) : BaseEntity() {
    companion object {
        fun toEntity(review: Review, user: User, reportCategory: ReportCategory): ReviewReport {
            return ReviewReport(
                review = review,
                category = reportCategory,
                user = user
            )
        }
    }
}
