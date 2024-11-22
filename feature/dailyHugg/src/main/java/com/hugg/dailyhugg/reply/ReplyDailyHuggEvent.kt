package com.hugg.dailyhugg.reply

import com.hugg.feature.base.Event

sealed class ReplyDailyHuggEvent: Event {
    data object CompleteReplyDailyHugg: ReplyDailyHuggEvent()
}