package com.hugg.data.api

object Endpoints {

    object AUTH{
        private const val AUTH = "/auth"
        const val LOGIN = "$AUTH/login"
        const val JOIN = "$AUTH/join"
        const val JOIN_MALE = "$AUTH/husbandJoin"
        const val GET_SHARE_CODE = "$AUTH/spouseCode"
        const val RENEWAL = "$AUTH/renewalToken"
        const val LOGOUT = "$AUTH/logout"
        const val UNREGISTER = "$AUTH/withdrawal"
        const val RENEWAL_FCM = "$AUTH/renewalFcm"
    }

    object RECORD {
        private const val RECORD = "/record"
        const val MODIFY = "$RECORD/{id}/modify"
        const val MEDICAL_RECORD = "$RECORD/{id}/medicalRecord"
        const val ADD = "$RECORD/add"
        const val MEDICAL_RECORD_AND_SIDE_EFFECT = "$RECORD/{id}/medicalRecordAndSideEffect"
        const val DELETE = "$RECORD/{id}/delete"
        const val DETAIL = "$RECORD/{id}/detail"
        const val SCHEDULE_LIST = "schedule"
        const val CHECK_TODO = "$RECORD/checkTodo/{id}"
    }

    object Home {
        const val HOME = "/home"
    }

    object DailyRecord {
        const val DAILY = "/daily"
        const val WRITE = "$DAILY/write"
        const val SIDEEFFECT = "$DAILY/sideEffect"
        const val SIDEEFFECTLIST = "$DAILY/sideEffectList"
        const val EMOTION = "$DAILY/{id}/emotion"
        const val SHARE_INJECTION = "$DAILY/shareInjection/{id}"
        const val GET_INJECTION_INFO = "$DAILY/injectionInfo/{id}"
        const val ID = "$DAILY/{id}"
    }

    object DailyHugg {
        const val DAILY = "/daily"
        const val WRITE = "$DAILY/write"
        const val BYDATE = "$DAILY/byDate/{date}"
        const val EDIT = "$DAILY/{id}"
        const val DELETE = "$DAILY/{id}"
    }

    object Challenge {
        const val CHALLENGE = "/challenge"
        const val NICKNAME = "$CHALLENGE/nickname"
        const val UNLOCK = "$CHALLENGE/unlock/{id}"
        const val ALL = "$CHALLENGE/all"
        const val CREATE = "$CHALLENGE/create"
        const val SUPPORT = "$CHALLENGE/{id}/participantsList"
        const val ACTION = "$CHALLENGE/{challengeId}"

        const val PARTICIPATION = "$CHALLENGE/participate/{id}"
        const val QUIT = "$CHALLENGE/quit/{id}"
        const val COMPLETE = "$CHALLENGE/complete/{id}"
        const val DELETE_COMPLETE = "$CHALLENGE/deleteTodayComplete/{id}"
        const val MY = "$CHALLENGE/my"
    }

    object ACCOUNT{
        private const val LEDGER = "/ledger"
        const val GET_BY_CONDITION = "$LEDGER/byCondition"
        const val GET_BY_MONTH = "$LEDGER/byMonth"
        const val GET_BY_COUNT = "$LEDGER/byCount"
        const val DELETE = "$LEDGER/{id}/delete"
        const val PUT_EDIT_MEMO = "$LEDGER/memo/{count}"
        const val DELETE_EXPENDITURE = "$LEDGER/expenditure/{id}"
        const val MODIFY = "$LEDGER/{id}/modify"
        const val CREATE = "$LEDGER/add"
        const val GET_DETAIL = "$LEDGER/{id}/detail"
        const val CREATE_COUNT = "$LEDGER/createCount"
    }

    object SUBSIDY{
        const val SUBSIDY = "/subsidy"
        const val DELETE_MODIFY_SUBSIDY = " $SUBSIDY/{id}"
        const val GET_SUBSIDY_BY_COUNT = "$SUBSIDY/byCount/{count}"
    }

    object PROFILE{
        const val MY_INFO = "/myPage"
        const val MODIFY = "$MY_INFO/modifySurgery"
        const val GET_MEDICAL = "$MY_INFO/medicalInfo"
    }

    object INFORMATION{
        const val ALL = "/information"
        const val BY_TYPE = "$ALL/bySort"
    }
}