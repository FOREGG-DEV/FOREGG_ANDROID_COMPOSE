package com.hugg.main.splash

import com.hugg.feature.base.Event

sealed class SplashEvent : Event {
    data object GoToMainEvent : SplashEvent()
    data object GoToSignEvent : SplashEvent()
}