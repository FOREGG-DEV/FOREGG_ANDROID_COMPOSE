package com.hugg.domain.model.vo.calendar

data class CreateScheduleTimeVo(
    val amOrPm : String = "",
    val hour : String = "",
    val minute : String = "",
){
    fun isEmpty() : Boolean{
        return amOrPm.isEmpty() || hour.isEmpty() || minute.isEmpty()
    }
}