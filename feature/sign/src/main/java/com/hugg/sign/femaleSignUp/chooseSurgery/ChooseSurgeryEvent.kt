package com.hugg.sign.femaleSignUp.chooseSurgery

import com.hugg.feature.base.Event

sealed class ChooseSurgeryEvent : Event{
    data class GoToSurgeryCountPage(val ssn : String) : ChooseSurgeryEvent()
    data class GoToSpouseCodePage(val ssn : String) : ChooseSurgeryEvent()
}