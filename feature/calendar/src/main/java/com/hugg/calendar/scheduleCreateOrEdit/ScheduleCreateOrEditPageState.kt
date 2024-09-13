package com.hugg.calendar.scheduleCreateOrEdit

import com.hugg.domain.model.enums.CreateOrEditType
import com.hugg.domain.model.enums.RecordType
import com.hugg.domain.model.vo.calendar.RepeatTimeVo
import com.hugg.feature.base.PageState
import com.hugg.feature.theme.CALENDAR_SCHEDULE_DEFAULT_TIME

data class ScheduleCreateOrEditPageState(
    val pageType : CreateOrEditType = CreateOrEditType.CREATE,
    val recordType: RecordType = RecordType.ETC,
    val id : Long = -1,
    val name : String = "",
    val isExpandDropDown : Boolean = false,
    val dose : String = "",
    val repeatCount : Int = 1,
    val repeatTimeList : List<RepeatTimeVo> = listOf(RepeatTimeVo(CALENDAR_SCHEDULE_DEFAULT_TIME)),
    val isAlarmCheck : Boolean = true,
) : PageState