package com.hugg.dailyhugg.all

import com.hugg.domain.model.response.dailyHugg.DailyHuggListItemVo
import com.hugg.feature.base.PageState

data class DailyHuggListPageState(
    val dailyHuggList: List<DailyHuggListItemVo> = emptyList(),
    val currentPage: Int = 0,
    val totalPages: Int = 0,
    val totalItems: Int = 0
): PageState