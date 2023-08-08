package com.psr.psr.global

class Constant {
    class JWT {
        companion object JWT {
            const val AUTHORIZATION_HEADER = "Authorization"
            const val BEARER_PREFIX: String = "Bearer "
        }
    }

    class UserStatus {
        companion object UserStatus {
            const val LOGOUT = "logout"
            const val ACTIVE_STATUS = "active"
            const val INACTIVE_STATUS = "inactive"
        }
    }

    class UserEID{
        companion object UserEID{
            const val EID_URL = "https://api.odcloud.kr/api/nts-businessman/v1/validate?serviceKey="
            const val PAY_STATUS = "01"
        }
    }

    class OrderType{
        companion object OrderType{
            const val SELL = "sell"
            const val ORDER = "order"
        }
    }

    class REPORT {
        companion object REPORT {
            const val CATEGORY = "category"
        }
    }
}