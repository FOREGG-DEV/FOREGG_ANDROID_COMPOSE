package com.hugg.domain.model.response.dailyHugg

import com.hugg.domain.model.enums.DailyConditionType
import com.hugg.domain.model.enums.DailyHuggReplyType

data class DailyHuggItemVo(
    val id: Long = -1,
    val time: String = "",
    val day: String = "",
    val dailyConditionType: DailyConditionType = DailyConditionType.DEFAULT,
    val content: String = "",
    val imageUrl: String? = null,
    val reply: String = "",
    val replyEmojiType: DailyHuggReplyType = DailyHuggReplyType.NOTHING,
    val specialQuestion: String = "",
)
