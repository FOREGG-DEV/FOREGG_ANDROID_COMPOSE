package com.hugg.sign.maleSignUp

import com.hugg.feature.base.Event

sealed class MaleSignUpEvent : Event{
    data object GoToMainEvent : MaleSignUpEvent()
    data object ErrorShareCode : MaleSignUpEvent()
}