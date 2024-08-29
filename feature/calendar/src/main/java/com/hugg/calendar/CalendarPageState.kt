package com.hugg.calendar

import com.hugg.domain.model.vo.calendar.CalendarDayVo
import com.hugg.feature.base.PageState

data class CalendarPageState(
    val selectedYearMonth : String = "",
    val calendarHeadList : List<CalendarDayVo> = emptyList(),
    val calendarDayList : List<CalendarDayVo> = emptyList(),
) : PageState