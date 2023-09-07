package com.psr.psr.user.entity

import com.psr.psr.global.entity.BaseEntity
import com.psr.psr.user.dto.request.SignUpReq
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.jetbrains.annotations.NotNull
import java.util.stream.Collectors

@Entity
@SQLDelete(sql = "UPDATE user_interest SET status = 'inactive', updated_at = current_timestamp WHERE id = ?")
data class UserInterest(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        @ManyToOne
        @JoinColumn(nullable = false, name = "user_id")
        var user: User,

        @NotNull
        @Enumerated(EnumType.STRING)
        var category: Category

): BaseEntity(){
        companion object{
                fun toInterestListEntity(user: User, signUpReq: SignUpReq): List<UserInterest> {
                        return signUpReq.interestList.stream()
                                .map { i ->
                                        UserInterest(category = Category.getCategoryByValue(i.category),
                                                user = user)
                                }.collect(Collectors.toList())
                }

                fun toInterestEntity(user: User, category: Category): UserInterest {
                        return UserInterest(user = user,
                                category = category)
                }
        }
}
