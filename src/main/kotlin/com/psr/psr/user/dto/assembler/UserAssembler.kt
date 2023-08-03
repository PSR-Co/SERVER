package com.psr.psr.user.dto.assembler

import com.psr.psr.user.dto.SignUpReq
import com.psr.psr.user.dto.UserEidReq
import com.psr.psr.user.dto.eidReq.Business
import com.psr.psr.user.dto.eidReq.BusinessListReq
import com.psr.psr.user.entity.*
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.stream.Collectors

@Component
class UserAssembler {
    fun toEntity(signUpReq: SignUpReq): User {
        return User(email = signUpReq.email,
            password = signUpReq.password,
            type = Type.getTypeByName(signUpReq.type),
            phone = signUpReq.phone,
            imgKey = signUpReq.imgKey,
            provider = Provider.LOCAL,
            marketing = signUpReq.marketing,
            notification = signUpReq.notification,
            nickname = signUpReq.nickname)
    }

    fun toInterestEntity(user: User, signUpReq: SignUpReq): List<UserInterest> {
        return signUpReq.interestList.stream()
            .map { i ->
                UserInterest(category = Category.getCategoryByName(i.category),
                    user = user)
            }.collect(Collectors.toList())
    }

    fun toBusinessEntity(user: User, signUpReq: SignUpReq): BusinessInfo {
        val format = DateTimeFormatter.ofPattern("yyyyMMdd")
        return BusinessInfo(user = user,
            companyName = signUpReq.entreInfo!!.companyName,
            ownerName = signUpReq.entreInfo.ownerName,
            number = signUpReq.entreInfo.number,
            date = LocalDate.parse(signUpReq.entreInfo.companyDate, format)
        )
    }

    fun toUserEidList(userEidReq: UserEidReq): BusinessListReq {
        val business = Business(userEidReq.number, userEidReq.companyDate, userEidReq.ownerName, userEidReq.companyName)
        return BusinessListReq(Collections.singletonList(business))
    }
}