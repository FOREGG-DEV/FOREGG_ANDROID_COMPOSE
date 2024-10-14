package com.hugg.data.dto.dailyHugg

import com.google.gson.annotations.SerializedName

data class DailyHuggListResponse(
    @SerializedName("dto")
    val dto: List<DailyHuggResponse>,
    @SerializedName("currentPage")
    val currentPage: Int,
    @SerializedName("totalPages")
    val totalPages: Int,
    @SerializedName("totalItems")
    val totalItems: Int
)
