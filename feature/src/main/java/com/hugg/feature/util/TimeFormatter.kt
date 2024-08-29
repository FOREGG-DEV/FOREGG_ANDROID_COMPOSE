package com.hugg.feature.util

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
}