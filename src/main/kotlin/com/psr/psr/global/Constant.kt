package com.psr.psr.global

class Constant {
    class JWT {
        companion object JWT {
            const val AUTHORIZATION_HEADER = "Authorization"
            const val BEARER_PREFIX: String = "Bearer "
        }
    }
    class USER_STATUS {
        companion object USER_STATUS {
            const val LOGOUT = "logout"
            const val ACTIVE_STATUS = "active"
            const val INACTIVE_STATUS = "inactive"
        }
    }
}