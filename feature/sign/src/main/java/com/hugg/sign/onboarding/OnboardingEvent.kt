package com.hugg.sign.onboarding

import com.hugg.feature.base.Event

sealed class OnboardingEvent : Event{
    data object MoveNextPage : OnboardingEvent()
    data object MovePrevPage : OnboardingEvent()
    data object MoveLastPage : OnboardingEvent()
    data object GoToMainEvent : OnboardingEvent()
    data class GoToSignUpEvent(val accessToken : String) : OnboardingEvent()
}