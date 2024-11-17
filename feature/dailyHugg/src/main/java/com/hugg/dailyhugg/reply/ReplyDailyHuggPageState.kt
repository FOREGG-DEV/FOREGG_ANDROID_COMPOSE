package com.hugg.dailyhugg.reply

import com.hugg.domain.model.enums.DailyHuggReplyType
import com.hugg.domain.model.response.dailyHugg.DailyHuggItemVo
import com.hugg.feature.base.PageState

data class ReplyDailyHuggPageState(
    val dailyHugg: DailyHuggItemVo? = null,
    val selectedReplyType: DailyHuggReplyType = DailyHuggReplyType.NOTHING,
): PageState