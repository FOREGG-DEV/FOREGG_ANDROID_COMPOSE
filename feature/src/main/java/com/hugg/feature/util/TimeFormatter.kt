package com.hugg.feature.util

import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

object TimeFormatter {

    fun getToday(): String {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return currentDate.format(formatter)
    }

    fun getDatePickerDashDate(year : Int, month : Int, day : Int) : String {
        val formattedMonth = String.format("%02d", month + 1)
        val formattedDay = String.format("%02d", day)
        return "$year-$formattedMonth-$formattedDay"
    }
}