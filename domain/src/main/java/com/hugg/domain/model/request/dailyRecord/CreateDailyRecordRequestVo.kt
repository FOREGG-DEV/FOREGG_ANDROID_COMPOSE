package com.hugg.domain.model.request.dailyRecord

import com.google.gson.annotations.SerializedName
import com.hugg.domain.model.enums.DailyConditionType

data class CreateDailyRecordRequestVo(
    @SerializedName("dailyConditionType")
    val dailyConditionType: DailyConditionType = DailyConditionType.SOSO,
    @SerializedName("content")
    val content: String = ""
)
