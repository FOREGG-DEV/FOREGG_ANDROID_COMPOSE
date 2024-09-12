package com.hugg.calendar.calendarMain

import com.hugg.domain.model.vo.calendar.CalendarDayVo
import com.hugg.feature.base.PageState

data class CalendarPageState(
    val selectedYearMonth : String = "",
    val calendarHeadList : List<CalendarDayVo> = emptyList(),
    val calendarDayList : List<CalendarDayVo> = emptyList(),
    val isShowDetailDialog : Boolean = false,
    val clickedPosition : Int = 0,
    val isCreateMode : Boolean = false,
    val showErrorMaxScheduleSnackBar : Boolean = false
) : PageState