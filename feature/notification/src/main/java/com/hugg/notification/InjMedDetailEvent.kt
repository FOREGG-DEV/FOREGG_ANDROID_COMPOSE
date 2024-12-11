package com.hugg.notification

import com.hugg.feature.base.Event

sealed class InjMedDetailEvent : Event{
    data object SuccessShareToast : InjMedDetailEvent()
    data object ErrorShareToast : InjMedDetailEvent()
}