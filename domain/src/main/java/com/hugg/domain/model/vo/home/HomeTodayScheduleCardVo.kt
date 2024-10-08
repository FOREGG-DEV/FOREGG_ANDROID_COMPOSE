package com.hugg.domain.model.vo.home

import com.hugg.domain.model.enums.RecordType

data class HomeTodayScheduleCardVo(
    val id: Long = -1,
    val recordType: RecordType = RecordType.ETC,
    val time : String = "",
    val name: String = "",
    val memo: String = "",
    val todo : Boolean = false,
    val isNearlyNowTime : Boolean = false
)
