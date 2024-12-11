package com.hugg.domain.base

object StatusCode {
    const val ERROR = "ERROR400"
    const val ERROR_404 = "ERROR404"
    const val NETWORK_ERROR = "NETWORK_ERROR"

    object AUTH {
        const val USER_NEED_JOIN = "USER4002"
        const val NOT_CORRECT_SHARE_CODE = "USER4004"
    }

    object RECORD {
        const val NO_EXIST_SCHEDULE = "RECORD4001"
    }

    object LEDGER {
        const val NO_EXIST_LEDGER = "LEDGER4001"
        const val EXCEED_SUBSIDY = "SUBSIDY4002"
    }

    object DAILY {
        const val SPOUSE_NOT_FOUND = "USER4005"
    }

    object DAILY_HUGG {
        const val ALREADY_EXIST_DAILY_HUGG = "DAILY4001"
        const val NO_EXIST_DAILY_HUGG = "DAILY4002"
    }

    object DAILY_RECORD {
        const val EXIST_DAILY_RECORD = "DAILY4001"
    }

    object CHALLENGE {
        const val DUPLICATE_NICKNAME = "CHALLENGE4008"
        const val EXIST_NICKNAME = "CHALLENGE4007"
        const val ALREADY_PARTICIPATED = "CHALLENGE4004"
        const val INSUFFICIENT_POINTS = "POINT4001"
        const val SUPPORT_LIMIT = "CHALLENGE4013"
    }
}