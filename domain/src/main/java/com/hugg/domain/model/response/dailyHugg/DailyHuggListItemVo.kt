package com.hugg.domain.model.response.dailyHugg

import com.hugg.domain.model.enums.DailyConditionType

data class DailyHuggListItemVo(
    val id: Long = -1,
    val date: String = "",
    val dailyConditionType: DailyConditionType = DailyConditionType.DEFAULT,
    val content: String = ""
)
