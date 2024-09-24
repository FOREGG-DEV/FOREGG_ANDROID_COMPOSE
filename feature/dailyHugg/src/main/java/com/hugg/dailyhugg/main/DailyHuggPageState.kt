package com.hugg.dailyhugg.main

import com.hugg.domain.model.response.dailyHugg.DailyHuggItemVo
import com.hugg.feature.base.PageState

data class DailyHuggPageState(
    val dailyHugg: DailyHuggItemVo? = null,
    val date: String = "",
    val day: String = "",
    val isInitialization: Boolean = true
): PageState