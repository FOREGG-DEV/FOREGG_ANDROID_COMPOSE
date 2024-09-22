package com.hugg.domain.model.vo.dailyHugg

import com.hugg.domain.model.enums.DailyConditionType

data class CreateDailyHuggDto(
    val dailyConditionType: DailyConditionType,
    val content: String
)
