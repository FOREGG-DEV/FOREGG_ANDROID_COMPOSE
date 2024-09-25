package com.hugg.domain.model.response.dailyHugg

import com.hugg.domain.model.enums.DailyConditionType

data class DailyHuggItemVo(
    val id: Long = -1,
    val date: String = "",
    val day: String = "",
    val dailyConditionType: String = "",
    val content: String = "",
    val imageUrl: String = "",
    val reply: String = ""
)
