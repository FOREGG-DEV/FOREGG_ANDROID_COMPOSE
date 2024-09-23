package com.hugg.calendar.scheduleCreateOrEdit

import com.hugg.feature.base.Event

sealed class ScheduleCreateOrEditEvent : Event {
    data object SuccessCreateScheduleEvent : ScheduleCreateOrEditEvent()
    data object SuccessModifyScheduleEvent : ScheduleCreateOrEditEvent()
    data object SuccessDeleteScheduleEvent : ScheduleCreateOrEditEvent()
}