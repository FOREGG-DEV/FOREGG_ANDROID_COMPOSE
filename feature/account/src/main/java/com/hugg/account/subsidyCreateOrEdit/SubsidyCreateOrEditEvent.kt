package com.hugg.account.subsidyCreateOrEdit

import com.hugg.feature.base.Event

sealed class SubsidyCreateOrEditEvent : Event {
    data object SuccessDeleteSubsidyEvent : SubsidyCreateOrEditEvent()
    data object SuccessCreateSubsidyEvent : SubsidyCreateOrEditEvent()
}