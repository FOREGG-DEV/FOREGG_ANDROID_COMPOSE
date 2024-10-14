package com.hugg.domain.model.response.dailyHugg

import com.google.gson.annotations.SerializedName

data class DailyHuggListResponseVo(
    val dailyHuggList: List<DailyHuggListItemVo> = emptyList(),
    val currentPage: Int = -1,
    val totalPages: Int = -1,
    val totalItems: Int = -1
)
