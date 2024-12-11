package com.hugg.support

import com.hugg.feature.base.Event

sealed class ChallengeSupportEvent: Event {
    data object ClapSuccess: ChallengeSupportEvent()
    data object SupportSuccess: ChallengeSupportEvent()
    data object ExceedSupportLimit: ChallengeSupportEvent()
}