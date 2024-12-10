package com.hugg.feature.util

import com.hugg.domain.model.vo.calendar.ScheduleDetailVo
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.DateTimeFormatterBuilder
import org.threeten.bp.format.TextStyle
import org.threeten.bp.temporal.ChronoField
import java.text.SimpleDateFormat
import java.util.Locale

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

    fun getDateFormattedMDWKor(date : String) : String {
        val date = LocalDate.parse(date)
        val month = date.monthValue
        val day = date.dayOfMonth
        val dayOfWeek = date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN)
        return "${month}월 ${day}일 ${dayOfWeek}"
    }

    fun getWeekListKor() : List<String>{
        return listOf("일", "월", "화", "수", "목", "금", "토")
    }


    fun getTodayYearAndMonthKor(year : Int, month : Int) : String {
        val formattedMonth = String.format("%02d", month)
        return "${year}년 ${formattedMonth}월"
    }

    fun getTodayYearMonthDayKor(date: String): String {
        val validDate = date.ifEmpty { getToday() }

        val dates = validDate.split("-")
        val year = dates[0]
        val month = dates[1]
        val day = dates[2]

        return "${year}년 ${month}월 ${day}일"
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
            datesBetween.add(vo.copy(date = currentDate.toString(), isContinueSchedule = !vo.repeatDate.isNullOrEmpty(), isStartContinueSchedule = currentDate == startLocalDate))
            currentDate = currentDate.plusDays(1)
        }

        return datesBetween
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

    fun getKoreanFullDayOfWeek(dateString: String): String {
        val formatter = DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-d"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-M-dd"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-M-d"))
            .toFormatter(Locale.KOREA)
        val date = LocalDate.parse(dateString, formatter)
        return when (date.dayOfWeek) {
            DayOfWeek.MONDAY -> "월요일"
            DayOfWeek.TUESDAY -> "화요일"
            DayOfWeek.WEDNESDAY -> "수요일"
            DayOfWeek.THURSDAY -> "목요일"
            DayOfWeek.FRIDAY -> "금요일"
            DayOfWeek.SATURDAY -> "토요일"
            DayOfWeek.SUNDAY -> "일요일"
        }
    }

    fun formatTimeToKor(timeString: String): String {
        val time = LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm"))
        val hour = time.hour
        val minute = time.minute
        val period = if (hour < 12) "오전" else "오후"
        val hour12 = if (hour % 12 == 0) 12 else hour % 12
        val formattedMinute = String.format("%02d", minute)
        return "$period ${hour12}:${formattedMinute}"
    }

    fun formatKorTimeToNormalTime(timeString : String) : String {
        val isPM = timeString.contains("오후")
        val timeParts = timeString.replace("오전 ", "").replace("오후 ", "").split(":")
        var hour = timeParts[0].toInt()
        val minute = timeParts[1]
        if (isPM && hour != 12) {
            hour += 12
        } else if (!isPM && hour == 12) {
            hour = 0
        }

        return String.format("%02d:%s", hour, minute)
    }

    fun getPreviousMonthDate() : String {
        val today = LocalDate.now()
        val oneMonthAgo = today.minusMonths(1)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return oneMonthAgo.format(formatter)
    }

    fun getPreviousThreeMonthDate() : String {
        val today = LocalDate.now()
        val oneMonthAgo = today.minusMonths(3)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return oneMonthAgo.format(formatter)
    }

    fun getDotsDate(dateString : String) : String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = LocalDate.parse(dateString, formatter)
        val newFormat = DateTimeFormatter.ofPattern("yyyy. MM. dd")
        return date.format(newFormat)
    }

    fun getPreviousMonthStartDay() : String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val currentMonthStart = LocalDate.now().withDayOfMonth(1)
        val previousMonthEnd = currentMonthStart.minusDays(1)
        val previousMonthStart = previousMonthEnd.withDayOfMonth(1)

        return previousMonthStart.format(formatter)
    }

    fun getPreviousMonthEndDay() : String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val currentMonthStart = LocalDate.now().withDayOfMonth(1)
        val previousMonthEnd = currentMonthStart.minusDays(1)

        return previousMonthEnd.format(formatter)
    }

    fun getDashDate(dateString : String) : String {
        val formatter = DateTimeFormatter.ofPattern("yyyy. MM. dd")
        val date = LocalDate.parse(dateString, formatter)
        val newFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return date.format(newFormat)
    }

    fun isAfter(date1 : String, date2 : String) : Boolean {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val originDate = LocalDate.parse(date1, formatter)
        val compareDate = LocalDate.parse(date2, formatter)

        return originDate.isAfter(compareDate)
    }

    fun getTimeFormatByKor(hour : Int, minute : Int) : String {
        val amPm = if (hour < 12) "오전" else "오후"
        val hourTime = if (hour % 12 == 0 && amPm == "오후") 12 else hour % 12
        return String.format("%s %d:%02d", amPm, hourTime, minute)
    }

    fun getPreviousDate(date: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val currentDate = LocalDate.parse(date, formatter)
        val previousDate = currentDate.minusDays(1)

        return previousDate.format(formatter)
    }

    fun getNextDate(date: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val currentDate = LocalDate.parse(date, formatter)
        val nextDate = currentDate.plusDays(1)

        return nextDate.format(formatter)
    }

    fun getParseNormalDashDate(date : String) : String {
        return date.split("-").let { parts ->
            "%04d-%02d-%02d".format(parts[0].toInt(), parts[1].toInt(), parts[2].toInt())
        }
    }

    fun getMonthDayWithSlash(date: String): String {
        val formatter = DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd HH:mm:ss")
            .optionalStart()
            .appendFraction(ChronoField.NANO_OF_SECOND, 1, 9, true)
            .optionalEnd()
            .toFormatter()

        val dateTime = LocalDateTime.parse(date, formatter)
        val outputFormatter = DateTimeFormatter.ofPattern("MM/dd")

        return dateTime.format(outputFormatter)
    }

    fun getTodayDayOfWeek(): String {
        val today = LocalDate.now()
        return today.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
    }
}