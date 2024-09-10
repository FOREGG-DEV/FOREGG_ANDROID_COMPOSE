package com.hugg.account.accountMain

import com.hugg.feature.base.Event

sealed class AccountEvent : Event {
    data object SuccessDeleteAccountEvent : AccountEvent()
}