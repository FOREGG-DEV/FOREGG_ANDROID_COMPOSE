package com.example.dailyhugg.create

import com.hugg.feature.base.Event

sealed class CreateDailyHuggEvent: Event {
    data object GoToDailyHuggCreationSuccess: CreateDailyHuggEvent()
    data object AlreadyExistDailyHugg: CreateDailyHuggEvent()
}