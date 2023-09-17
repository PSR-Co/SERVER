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

    class UserPhone{
        companion object UserPhone{
            const val SPACE = " "
            const val NEWLINE = "\n"
            const val METHOD = "POST"
            const val FIRST_URL = "https://sens.apigw.ntruss.com"
            const val MIDDLE_URL = "/sms/v2/services/"
            const val FINAL_URL = "/messages"
            const val TIMESTAMP_HEADER = "x-ncp-apigw-timestamp"
            const val ACCESS_KEY_HEADER = "x-ncp-iam-access-key"
            const val SIGNATURE_HEADER = "x-ncp-apigw-signature-v2"
            const val UTF_8 = "UTF-8"
            const val SETTING_ALGORITHM = "HmacSHA256"
        }
    }

    class Order{
        companion object Order{
            const val SELL = "sell"
            const val ORDER = "order"
            const val STATUS = "status"
        }
    }

    class REPORT {
        companion object REPORT {
            const val CATEGORY = "category"
        }
    }

    class SortType{
        companion object OrderType{
            const val RECENT = "최신순"
            const val POPULAR = "인기순"
        }
    }

    class NotiSentence{
        companion object NotiSentence{
            const val NEW_ORDER_SENTENCE = "님의 요청을 확인해주세요!"
            const val TWO_MONTH_ORDER_SENTENCE = "님의 요청 상태를 확인해주세요!"
        }
    }
}