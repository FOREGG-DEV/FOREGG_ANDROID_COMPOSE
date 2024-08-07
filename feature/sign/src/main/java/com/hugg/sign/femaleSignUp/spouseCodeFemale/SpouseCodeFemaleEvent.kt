package com.hugg.sign.femaleSignUp.spouseCodeFemale

import com.hugg.feature.base.Event

sealed class SpouseCodeFemaleEvent : Event{
    data object GoToMainEvent : SpouseCodeFemaleEvent()
}