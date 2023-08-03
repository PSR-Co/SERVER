package com.psr.psr.user.dto

import com.psr.psr.user.entity.*
import jakarta.annotation.Nullable
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern
import org.jetbrains.annotations.NotNull
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.stream.Collectors


data class SignUpReq (
    @field:NotBlank
    @field:Email(message = "올바르지 않은 이메일 형식입니다.")
    val email: String,
    @field:NotBlank
    @field:Pattern(
        regexp = "^.*(?=^.{8,15}\$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#\$%^&+=]).*\$",
        message = "비밀번호를 숫자, 문자, 특수문자 포함 8~15자리 이내로 입력해주세요"
    )
    var password: String,
    @field:NotBlank
    val type: String,
    @field:Pattern(
        regexp = "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})\$",
        message = "올바르지 않은 휴대폰 형식입니다."
    )
    val phone: String,
    @field:Nullable
    val imgKey: String? = null,
    @field:NotBlank
    @field:Pattern(
        regexp = "^([a-zA-Z0-9ㄱ-ㅎ|ㅏ-ㅣ|가-힣]).{0,10}\$",
        message = "한글, 영어, 숫자만 입력해주세요. (10글자)"
    )
    val nickname: String,
    val marketing: Boolean,
    @field:NotNull
    val notification: Boolean,
    @field:NotEmpty
    val interestList: List<UserInterestReq>,
    val entreInfo: UserEidReq ?= null
    ) {
    fun toEntity(): User {
        return User(email = email,
            password = password,
            type = Type.getTypeByName(type),
            phone = phone,
            imgKey = imgKey,
            provider = Provider.LOCAL,
            marketing = marketing,
            notification = notification,
            nickname = nickname)
    }

    fun toInterestEntity(user: User): List<UserInterest> {
        return interestList.stream()
            .map { i ->
                UserInterest(category = Category.getCategoryByName(i.category),
                    user = user)
            }.collect(Collectors.toList())
    }

    fun toBusinessEntity(user: User): BusinessInfo{
        val format = DateTimeFormatter.ofPattern("yyyyMMdd")
        return BusinessInfo(user = user,
            companyName = entreInfo!!.companyName,
            ownerName = entreInfo.ownerName,
            number = entreInfo.number,
            // todo: pr이 merge 되면 address => date로 변경
            date = LocalDate.parse(entreInfo.companyDate, format)
        )
    }
}