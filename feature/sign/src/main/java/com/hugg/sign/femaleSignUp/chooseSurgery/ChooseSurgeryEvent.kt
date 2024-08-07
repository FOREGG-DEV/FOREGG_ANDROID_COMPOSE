package com.hugg.sign.femaleSignUp.chooseSurgery

import com.hugg.feature.base.Event

sealed class ChooseSurgeryEvent : Event{
    data object GoToSurgeryCountPage : ChooseSurgeryEvent()
    data object GoToSpouseCodePage : ChooseSurgeryEvent()
}