package com.psr.psr.notification.repository

import com.psr.psr.notification.dto.NotiList
import com.psr.psr.notification.dto.NotificationListRes
import com.psr.psr.notification.entity.QPushNotification.pushNotification
import com.psr.psr.user.entity.User
import com.querydsl.core.group.GroupBy.groupBy
import com.querydsl.core.group.GroupBy.list
import com.querydsl.core.types.ConstantImpl
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.core.types.dsl.StringTemplate
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class NotificationRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : NotificationCustom {
    override fun findNotificationByUserGroupByDate(user: User, pageable: Pageable): Page<NotificationListRes> {
        val formattedDate: StringTemplate = Expressions.stringTemplate(
            "DATE_FORMAT({0}, {1})",
            pushNotification.createdAt,
            ConstantImpl.create("%Y-%m-%d")
        )

        val result = queryFactory
            .selectFrom(pushNotification)
            .where(pushNotification.user.eq(user))
            .orderBy(pushNotification.id.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .transform(groupBy(formattedDate)
                .list(Projections.constructor(NotificationListRes::class.java, formattedDate,
                    list(Projections.constructor(NotiList::class.java, pushNotification.title, pushNotification.content)))))
        return PageImpl(result, pageable, result.size.toLong())
    }

}