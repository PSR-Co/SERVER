package com.psr.psr.user.dto.eidReq

import com.psr.psr.user.dto.request.UserEidReq
import jakarta.validation.constraints.NotBlank
import java.util.*

data class BusinessListReq (
    @field:NotBlank
    val businesses: List<Business>
){
    companion object{
        fun toUserEidList(userEidReq: UserEidReq): BusinessListReq {
            val business = Business(userEidReq.number, userEidReq.companyDate, userEidReq.ownerName, userEidReq.companyName)
            return BusinessListReq(Collections.singletonList(business))
        }
    }
}