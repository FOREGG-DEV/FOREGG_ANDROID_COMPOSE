package com.hugg.data.dto

import com.google.gson.annotations.SerializedName
import com.hugg.domain.model.enums.DailyConditionType
import com.hugg.domain.model.response.home.HomeRecordResponseVo

data class HomeResponse(
    @SerializedName("userName")
    val userName: String = "",
    @SerializedName("spouseName")
    val spouseName: String? = "",
    @SerializedName("todayDate")
    val todayDate: String = "",
    @SerializedName("homeRecordResponseDTO")
    val homeRecordResponseVo: List<HomeRecordResponseVo> = emptyList(),
    @SerializedName("ssn")
    val ssn: String = "",
    @SerializedName("todo")
    val todo: Boolean = false,
    @SerializedName("dailyConditionType")
    val dailyConditionType: DailyConditionType? = DailyConditionType.DEFAULT,
    @SerializedName("dailyContent")
    val dailyContent: String? = "",
    @SerializedName("latestMedicalRecord")
    val latestMedicalRecord: String? = "",
    @SerializedName("medicalRecordId")
    val medicalRecordId: Long = -1
)
