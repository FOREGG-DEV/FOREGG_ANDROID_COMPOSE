package com.hugg.calendar.calendarMain

import com.hugg.domain.model.enums.RecordType
import com.hugg.feature.base.Event

sealed class CalendarEvent : Event {
    data class GoToCreateSchedule(val type : RecordType, val day : String) : CalendarEvent()
}