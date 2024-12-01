package com.hugg.create

import com.hugg.feature.base.Event

sealed class CreateChallengeEvent: Event {
    data object CreateChallengeCompleted: CreateChallengeEvent()
    data object InSufficientPoint: CreateChallengeEvent()
}