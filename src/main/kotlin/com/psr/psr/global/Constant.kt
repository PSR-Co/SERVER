package com.psr.psr.global

class Constant {
    class JWT{
        companion object JWT{
            const val AUTHORIZATION_HEADER = "Authorization"
            const val BEARER_PREFIX: String = "Bearer "
        }
    }

    class User{
        companion object User{
            // 비밀번호 (숫자, 문자, 특수문자 포함 8~15자리 이내)
            const val PASSWORD_VALIDATION = "^.*(?=^.{8,15}\$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#\$%^&+=]).*\$"
            const val EMAIL_VALIDATION = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}\$"
            const val PHONE_VALIDATION = "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})\$"
        }
    }

}