package com.hugg.calendar

import com.hugg.domain.model.enums.DayType
import com.hugg.domain.model.vo.calendar.CalendarDayVo
import com.hugg.feature.base.BaseViewModel
import com.hugg.feature.util.TimeFormatter
import com.hugg.feature.util.TimeFormatter.getWeekListKor
import dagger.hilt.android.lifecycle.HiltViewModel
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class CalendarViewModel @Inject constructor(
) : BaseViewModel<CalendarPageState>(
    CalendarPageState()
) {

    companion object{
        const val JANUARY = 1
        const val DECEMBER = 12
    }

    private var year by Delegates.notNull<Int>()
    private var month by Delegates.notNull<Int>()

    init {
        initData()
    }

    private fun initData(){
        val today = TimeFormatter.getToday()
        year = TimeFormatter.getYear(today)
        month = TimeFormatter.getMonth(today)
        setCalendar()
    }

    private fun setCalendar(){
        updateState(
            uiState.value.copy(
                selectedYearMonth = TimeFormatter.getTodayYearAndMonthKor(year, month),
                calendarHeadList = getHeadDayList(),
                calendarDayList = getMonthDays()
            )
        )
    }

    fun onClickNextMonth(){
        if(month == DECEMBER){
            year++
            month = JANUARY
        }
        else{
            month++
        }
        setCalendar()
    }

    fun onClickPrevMonth(){
        if(month == JANUARY){
            year--
            month = DECEMBER
        }
        else{
            month--
        }
        setCalendar()
    }

    private fun getHeadDayList() : List<CalendarDayVo>{
        val list = getWeekListKor().map {
            CalendarDayVo(
                day = it,
                isSunday = it == "일"
            )
        }

        return list
    }

    private fun getMonthDays() : List<CalendarDayVo>{
        val startOfMonth = YearMonth.of(year, month).atDay(1)
        val endOfMonth = YearMonth.of(year, month).atEndOfMonth()
        val dayList = mutableListOf<CalendarDayVo>()
        var currentDate = startOfMonth
        while (currentDate.isBefore(endOfMonth) || currentDate.isEqual(endOfMonth)) {
            val dateString = currentDate.toString()
            val isToday = dateString == TimeFormatter.getToday()
            val isSunday = currentDate.dayOfWeek == DayOfWeek.SUNDAY
            dayList.add(CalendarDayVo(day = TimeFormatter.getDay(dateString).toString(), isToday = isToday, isSunday = isSunday, dayType = DayType.NORMAL))
            currentDate = currentDate.plusDays(1)
        }
        return getPreviousMonthDays() + dayList + getNextMonthDays()
    }

    private fun getPreviousMonthDays() : List<CalendarDayVo>{
        val startDay = YearMonth.of(year, month).atDay(1).dayOfWeek.value
        if(startDay == 7) return emptyList()
        val dayList = mutableListOf<CalendarDayVo>()
        val lastDayOfPreviousMonth = YearMonth.of(year, month).minusMonths(1).atEndOfMonth()
        (1 .. startDay).map { i ->
            val day = lastDayOfPreviousMonth.minusDays((startDay - i).toLong()).toString()
            val isSunday = lastDayOfPreviousMonth.minusDays((startDay - i).toLong()).dayOfWeek == DayOfWeek.SUNDAY
            dayList.add(CalendarDayVo(day = TimeFormatter.getDay(day).toString(), isSunday = isSunday, dayType = DayType.PREV_NEXT))
        }
        return dayList
    }

    private fun getNextMonthDays() : List<CalendarDayVo>{
        val endDay = if(YearMonth.of(year, month).atEndOfMonth().dayOfWeek.value == 7) 0 else YearMonth.of(year, month).atEndOfMonth().dayOfWeek.value
        val firstDayOfNextMonth = YearMonth.of(year, month).plusMonths(1).atDay(1)
        val dayList = mutableListOf<CalendarDayVo>()
        (0 until (6 - endDay)).map {i ->
            val day = firstDayOfNextMonth.plusDays(i.toLong()).toString()
            val isSunday = firstDayOfNextMonth.plusDays(i.toLong()).dayOfWeek == DayOfWeek.SUNDAY
            dayList.add(CalendarDayVo(day = TimeFormatter.getDay(day).toString(), isSunday = isSunday, dayType = DayType.PREV_NEXT))
        }
        return dayList
    }
}