package com.hugg.domain.model.response.home

import com.hugg.domain.model.enums.DailyConditionType

data class HomeResponseVo (
    val userName: String = "",
    val spouseName: String = "",
    val todayDate: String = "",
    val homeRecordResponseVo: List<HomeRecordResponseVo> = emptyList(),
    val ssn: String = "",
    val dailyConditionType: DailyConditionType = DailyConditionType.DEFAULT,
    val dailyContent: String = "",
    val latestMedicalRecord: String = "",
    val medicalRecordId: Long = -1
)