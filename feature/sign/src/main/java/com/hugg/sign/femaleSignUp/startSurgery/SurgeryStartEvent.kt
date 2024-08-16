package com.hugg.sign.femaleSignUp.startSurgery

import com.hugg.feature.base.Event

sealed class SurgeryStartEvent : Event{
    data object GoToSpouseCodePage : SurgeryStartEvent()
}