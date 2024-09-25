package com.hugg.dailyhugg.create

import com.hugg.feature.base.Event

sealed class CreateEditDailyHuggEvent: Event {
    data object GoToDailyHuggCreationSuccess: CreateEditDailyHuggEvent()
    data object AlreadyExistEditDailyHugg: CreateEditDailyHuggEvent()
    data object CompleteEditDailyHugg: CreateEditDailyHuggEvent()
}