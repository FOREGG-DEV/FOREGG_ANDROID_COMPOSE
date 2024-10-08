package com.hugg.domain.model.response.home

import com.hugg.domain.model.enums.DailyConditionType

data class HomeResponseVo (
    val homeRecordResponseVo: List<HomeRecordResponseVo> = emptyList(),
    val dailyConditionType: DailyConditionType = DailyConditionType.DEFAULT,
    val dailyContent: String = "",
    val latestMedicalRecord: String = "",
    val medicalRecordId: Long = -1,
)