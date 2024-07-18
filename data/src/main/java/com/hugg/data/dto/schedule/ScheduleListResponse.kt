package com.hugg.data.dto.schedule

import com.google.gson.annotations.SerializedName
import com.hugg.domain.model.vo.calendar.ScheduleDetailVo

data class ScheduleListResponse(
    @SerializedName("records")
    val records : List<ScheduleDetailVo> = emptyList()
)