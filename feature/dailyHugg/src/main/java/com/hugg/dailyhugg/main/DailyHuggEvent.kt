package com.hugg.dailyhugg.main

import com.hugg.feature.base.Event

sealed class DailyHuggEvent: Event {
    data object CompleteDeleteDailyHugg: DailyHuggEvent()
    data object GoToDailyHuggList: DailyHuggEvent()
}