package com.hugg.feature.util

import com.hugg.domain.model.vo.calendar.ScheduleDetailVo
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

object TimeFormatter {

    private const val YEAR_INDEX = 0
    private const val MONTH_INDEX = 1
    private const val DAY_INDEX = 2

    fun getToday(): String {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return currentDate.format(formatter)
    }

    fun getYear(date : String) : Int {
        val dates = date.split("-")
        return dates[YEAR_INDEX].toInt()
    }

    fun getMonth(date : String) : Int {
        val dates = date.split("-")
        return dates[MONTH_INDEX].toInt()
    }

    fun getDay(date : String) : Int {
        val dates = date.split("-")
        return dates[DAY_INDEX].toInt()
    }

    fun getWeekListKor() : List<String>{
        return listOf("일", "월", "화", "수", "목", "금", "토")
    }


    fun getTodayYearAndMonthKor(year : Int, month : Int) : String {
        val formattedMonth = String.format("%02d", month)
        return "${year}년 ${formattedMonth}월"
    }

    fun getDatePickerDashDate(year : Int, month : Int, day : Int) : String {
        val formattedMonth = String.format("%02d", month + 1)
        val formattedDay = String.format("%02d", day)
        return "$year-$formattedMonth-$formattedDay"
    }

    fun getDatesBetween(vo : ScheduleDetailVo): List<ScheduleDetailVo> {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val startLocalDate = LocalDate.parse(vo.startDate, formatter)
        val endLocalDate = LocalDate.parse(vo.endDate, formatter)

        val datesBetween = mutableListOf<ScheduleDetailVo>()
        var currentDate = startLocalDate

        while (!currentDate.isAfter(endLocalDate)) {
            if (isContainDayOfWeek(vo.repeatDate?.split(", ")?.map { it.trim() }, currentDate)) {
                datesBetween.add(vo.copy(date = currentDate.toString(), isContinueSchedule = currentDate != startLocalDate, isStartContinueSchedule = currentDate == startLocalDate))
            }
            currentDate = currentDate.plusDays(1)
        }

        return datesBetween
    }

    private fun isContainDayOfWeek(weekdays : List<String>?, day : LocalDate) : Boolean{
        val dayOfWeekKorean = getKoreanDayOfWeek(day.dayOfWeek)
        return weekdays?.contains("매일") == true ||
                weekdays?.contains(dayOfWeekKorean) == true
    }

    fun getKoreanDayOfWeek(dayOfWeek: DayOfWeek): String {
        return when (dayOfWeek) {
            DayOfWeek.MONDAY -> "월"
            DayOfWeek.TUESDAY -> "화"
            DayOfWeek.WEDNESDAY -> "수"
            DayOfWeek.THURSDAY -> "목"
            DayOfWeek.FRIDAY -> "금"
            DayOfWeek.SATURDAY -> "토"
            DayOfWeek.SUNDAY -> "일"
        }
    }
}