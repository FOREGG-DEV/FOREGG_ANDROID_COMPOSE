package com.hugg.domain.model.vo.calendar

import com.google.gson.annotations.SerializedName
import com.hugg.domain.model.enums.RecordType

data class ScheduleDetailVo(
    @SerializedName("id")
    val id : Long = -1,
    @SerializedName("recordType")
    val recordType: RecordType = RecordType.MEDICINE,
    @SerializedName("name")
    val name : String = "",
    @SerializedName("date")
    val date : String? = null,
    @SerializedName("startDate")
    val startDate : String? = null,
    @SerializedName("endDate")
    val endDate : String? = null,
    @SerializedName("repeatDate")
    val repeatDate : String? = null,
    @SerializedName("repeatTimes")
    val repeatTimes : List<RepeatTimeVo> = emptyList(),
    @SerializedName("dose")
    val dose : String? = null,
    @SerializedName("memo")
    val memo : String = "",
    @SerializedName("vibration")
    val vibration : Boolean = false,

    val isStartContinueSchedule : Boolean = false,
    val isContinueSchedule : Boolean = false,
    val blankCount : Int = 0
)