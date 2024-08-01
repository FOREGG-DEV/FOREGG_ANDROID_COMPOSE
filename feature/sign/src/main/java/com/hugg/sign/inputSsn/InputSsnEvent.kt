package com.hugg.sign.inputSsn

import com.hugg.feature.base.Event

sealed class InputSsnEvent : Event{
    data class GoToFemaleSignUp(val ssn : String) : InputSsnEvent()
    data class GoToMaleSignUp(val ssn : String) : InputSsnEvent()
    data class FocusTextFiled(val position : Int) : InputSsnEvent()
    data object HideKeyboard : InputSsnEvent()
}