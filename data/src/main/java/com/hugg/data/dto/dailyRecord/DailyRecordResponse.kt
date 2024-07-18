package com.hugg.data.dto.dailyRecord

import com.google.gson.annotations.SerializedName

data class DailyRecordResponse(
    @SerializedName("dailyResponseDTO")
    val dailyResponseDTO: List<DailyRecordResponseItem> = emptyList()
)
