package com.hugg.sign.inputSsn

import com.hugg.feature.base.Event

sealed class InputSsnEvent : Event{
    data object GoToFemaleSignUp : InputSsnEvent()
    data object GoToMaleSignUp : InputSsnEvent()
    data class FocusTextFiled(val position : Int) : InputSsnEvent()
}