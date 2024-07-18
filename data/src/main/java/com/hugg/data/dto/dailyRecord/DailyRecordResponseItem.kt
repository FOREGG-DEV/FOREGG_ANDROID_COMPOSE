package com.hugg.data.dto.dailyRecord

import com.google.gson.annotations.SerializedName
import com.hugg.domain.model.enums.DailyConditionType
import com.hugg.domain.model.enums.EmotionType

data class DailyRecordResponseItem(
    @SerializedName("id")
    val id: Long = -1,
    @SerializedName("dailyConditionType")
    val dailyConditionType: DailyConditionType = DailyConditionType.DEFAULT,
    @SerializedName("content")
    val content: String = "",
    @SerializedName("date")
    val date: String = "",
    @SerializedName("emotionType")
    val emotionType: EmotionType? = EmotionType.DEFAULT
)
