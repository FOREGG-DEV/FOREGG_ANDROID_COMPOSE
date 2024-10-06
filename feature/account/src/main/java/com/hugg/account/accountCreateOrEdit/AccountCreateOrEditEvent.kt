package com.hugg.account.accountCreateOrEdit

import com.hugg.feature.base.Event


sealed class AccountCreateOrEditEvent : Event {
    data object SuccessCreateAccountEvent : AccountCreateOrEditEvent()
    data object SuccessModifyAccountEvent : AccountCreateOrEditEvent()
    data object SuccessDeleteAccountEvent : AccountCreateOrEditEvent()
    data object ErrorExceedSubsidyEvent : AccountCreateOrEditEvent()
}