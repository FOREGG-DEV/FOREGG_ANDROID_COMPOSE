package com.hugg.data.dto.dailyHugg

import com.google.gson.annotations.SerializedName
import com.hugg.domain.model.enums.DailyConditionType

data class DailyHuggResponse(
    @SerializedName("id")
    val id: Long = -1,
    @SerializedName("date")
    val date: String = "",
    @SerializedName("day")
    val day: String = "",
    @SerializedName("dailyConditionType")
    val dailyConditionType: String = "",
    @SerializedName("content")
    val content: String = "",
    @SerializedName("imageUrl")
    val imageUrl: String = "",
    @SerializedName("reply")
    val reply: String? = null
)
