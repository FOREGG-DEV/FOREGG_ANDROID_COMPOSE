package com.hugg.sign

import com.hugg.feature.base.Event

sealed class OnboardingEvent : Event{
    data object MoveNextPage : OnboardingEvent()
    data object MovePrevPage : OnboardingEvent()
    data object MoveLastPage : OnboardingEvent()
}