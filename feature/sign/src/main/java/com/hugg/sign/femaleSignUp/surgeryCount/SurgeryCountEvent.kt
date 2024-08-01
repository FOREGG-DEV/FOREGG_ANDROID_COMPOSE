package com.hugg.sign.femaleSignUp.surgeryCount

import com.hugg.feature.base.Event

sealed class SurgeryCountEvent : Event{
    data object GoToSurgeryStartDayPage : SurgeryCountEvent()
}