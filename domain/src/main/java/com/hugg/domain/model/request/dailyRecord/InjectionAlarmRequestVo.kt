package com.hugg.domain.model.request.dailyRecord

data class InjectionAlarmRequestVo(
    val id : Long = -1,
    val type : String = "",
    val date : String = "",
    val time : String = ""
)
