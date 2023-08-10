package com.psr.psr.user.dto.assembler

import com.psr.psr.global.Constant
import com.psr.psr.global.jwt.dto.TokenDto
import com.psr.psr.user.dto.response.MyPageInfoRes
import com.psr.psr.user.dto.response.ProfileRes
import com.psr.psr.user.dto.eidReq.Business
import com.psr.psr.user.dto.eidReq.BusinessListReq
import com.psr.psr.user.dto.phoneReq.MessageReq
import com.psr.psr.user.dto.phoneReq.SMSReq
import com.psr.psr.user.dto.request.SignUpReq
import com.psr.psr.user.dto.request.UserEidReq
import com.psr.psr.user.dto.request.ValidPhoneReq
import com.psr.psr.user.dto.response.EmailRes
import com.psr.psr.user.entity.*
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.stream.Collectors

@Component
class UserAssembler {
    /**
     * toEntity
     */
    fun toEntity(signUpReq: SignUpReq): User {
        return User(email = signUpReq.email,
            password = signUpReq.password,
            type = Type.getTypeByName(signUpReq.type),
            phone = signUpReq.phone,
            imgUrl = signUpReq.imgUrl,
            provider = Provider.LOCAL,
            marketing = signUpReq.marketing,
            notification = signUpReq.notification,
            name = signUpReq.name,
            nickname = signUpReq.nickname)
    }

    fun toInterestListEntity(user: User, signUpReq: SignUpReq): List<UserInterest> {
        return signUpReq.interestList.stream()
            .map { i ->
                UserInterest(category = Category.getCategoryByName(i.category),
                    user = user)
            }.collect(Collectors.toList())
    }

    fun toInterestEntity(user: User, category: Category): UserInterest {
        return UserInterest(user = user,
            category = category)
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

    /**
     * toDto
     */
    fun toMyPageInfoRes(user: User) : MyPageInfoRes {
        return MyPageInfoRes(user.email, user.imgUrl, user.type.value, user.phone)
    }

    fun toProfileRes(user: User) : ProfileRes {
        return ProfileRes(user.email, user.imgUrl)
    }

    fun toTokenDto(tokenDto: TokenDto) {
        tokenDto.accessToken.replace(Constant.JWT.BEARER_PREFIX, "").also { tokenDto.accessToken = it }
        tokenDto.refreshToken.replace(Constant.JWT.BEARER_PREFIX, "").also { tokenDto.refreshToken = it }
    }

    fun toSMSReqDto(validPhoneReq: ValidPhoneReq, key: String, sendPhone: String) : SMSReq{
        val message = MessageReq(to = validPhoneReq.phone)
        return SMSReq(
            from = sendPhone,
            content = "[PSR] 인증번호는 [ $key ] 을 입력해주세요",
            messages = listOf(message) // 싱글톤 list = Collections.singletonList()
        )
    }

    fun toEmailResDto(user: User): EmailRes {
        return EmailRes(email = user.email)
    }

    /**
     * Utils
     */
    fun createSmsKey() : String{
        return RandomStringUtils.random(5, false, true);
    }
}