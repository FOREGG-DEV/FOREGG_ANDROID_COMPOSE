package com.hugg.calendar

import androidx.lifecycle.viewModelScope
import com.hugg.domain.model.enums.DayType
import com.hugg.domain.model.vo.calendar.CalendarDayVo
import com.hugg.domain.model.vo.calendar.ScheduleDetailVo
import com.hugg.domain.repository.ScheduleRepository
import com.hugg.feature.base.BaseViewModel
import com.hugg.feature.util.ForeggLog
import com.hugg.feature.util.TimeFormatter
import com.hugg.feature.util.TimeFormatter.getWeekListKor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.threeten.bp.DayOfWeek
import org.threeten.bp.YearMonth
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val scheduleRepository: ScheduleRepository
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
            )
        )
        getScheduleList()
    }

    private fun updateCalendar(list : List<ScheduleDetailVo>){
        updateState(
            uiState.value.copy(
                calendarDayList = getMonthDays(list),
            )
        )
    }

    private fun getScheduleList(){
        viewModelScope.launch {
            scheduleRepository.getScheduleList(getRequest()).collect {
                resultResponse(it, ::updateCalendar, needLoading = true)
            }
        }
    }

    private fun getRequest() : String{
        val requestYear = String.format("%02d", year)
        val requestMonth = String.format("%02d", month)
        return "$requestYear-$requestMonth"
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

    private fun getMonthDays(list: List<ScheduleDetailVo>) : List<CalendarDayVo>{
        val newScheduleList = getArrangeRepeatScheduleList(list)
        val startOfMonth = YearMonth.of(year, month).atDay(1)
        val endOfMonth = YearMonth.of(year, month).atEndOfMonth()
        val dayList = mutableListOf<CalendarDayVo>()
        var currentDate = startOfMonth
        while (currentDate.isBefore(endOfMonth) || currentDate.isEqual(endOfMonth)) {
            val dateString = currentDate.toString()
            val isToday = dateString == TimeFormatter.getToday()
            val scheduleList = newScheduleList.filter { it.date == dateString }.sortedWith(compareBy({ it.recordType.priority }, { it.repeatTimes.first().time }))
            val isSunday = currentDate.dayOfWeek == DayOfWeek.SUNDAY
            dayList.add(CalendarDayVo(day = TimeFormatter.getDay(dateString).toString(), scheduleList = scheduleList, isToday = isToday, isSunday = isSunday, dayType = DayType.NORMAL))
            currentDate = currentDate.plusDays(1)
        }

        val monthDayList = getPreviousMonthDays(newScheduleList) + dayList + getNextMonthDays(newScheduleList)
        return getBlankCount(monthDayList)
    }

    private fun getBlankCount(list: List<CalendarDayVo>): List<CalendarDayVo> {
        val idToMaxBlankCountMap = mutableMapOf<Long, Int>()

        list.forEach { calendarDayVo ->
            calendarDayVo.scheduleList.forEachIndexed { index, scheduleDetailVo ->
                if (scheduleDetailVo.isContinueSchedule || scheduleDetailVo.isStartContinueSchedule) {
                    idToMaxBlankCountMap[scheduleDetailVo.id] = maxOf(idToMaxBlankCountMap.getOrDefault(scheduleDetailVo.id, 0), index)
                }
            }
        }
        val updatedCalendarDayVoList = list.map { calendarDayVo ->
            val updatedScheduleList = calendarDayVo.scheduleList.map { scheduleDetailVo ->
                scheduleDetailVo.copy(blankCount = idToMaxBlankCountMap.getOrDefault(scheduleDetailVo.id, 0))
            }

            calendarDayVo.copy(scheduleList = updatedScheduleList)
        }

        return updatedCalendarDayVoList
    }

    private fun getArrangeRepeatScheduleList(list : List<ScheduleDetailVo>) : List<ScheduleDetailVo>{
        val normalScheduleList = list.filter { it.date != null }
        val repeatScheduleList = list.filter { it.date == null }
        val newList = mutableListOf<ScheduleDetailVo>()
        repeatScheduleList.forEach { vo ->
            newList.addAll(TimeFormatter.getDatesBetween(vo))
        }

        return getSplitTimeScheduleList(normalScheduleList + newList)
    }

    private fun getSplitTimeScheduleList(list: List<ScheduleDetailVo>) : List<ScheduleDetailVo>{
        val newList = mutableListOf<ScheduleDetailVo>()
        list.forEach {
            it.repeatTimes.forEach { time ->
                val newSchedule = it.copy(repeatTimes = listOf(time))
                newList.add(newSchedule)
            }
        }

        return newList
    }


    private fun getPreviousMonthDays(list: List<ScheduleDetailVo>) : List<CalendarDayVo>{
        val startDay = YearMonth.of(year, month).atDay(1).dayOfWeek.value
        if(startDay == 7) return emptyList()
        val dayList = mutableListOf<CalendarDayVo>()
        val lastDayOfPreviousMonth = YearMonth.of(year, month).minusMonths(1).atEndOfMonth()
        (1 .. startDay).map { i ->
            val day = lastDayOfPreviousMonth.minusDays((startDay - i).toLong()).toString()
            val scheduleListForDay = list.filter { it.date == day }.sortedWith(compareBy({ it.recordType.priority }, { it.repeatTimes.first().time }))
            val isSunday = lastDayOfPreviousMonth.minusDays((startDay - i).toLong()).dayOfWeek == DayOfWeek.SUNDAY
            dayList.add(CalendarDayVo(day = TimeFormatter.getDay(day).toString(), scheduleList = scheduleListForDay, isSunday = isSunday, dayType = DayType.PREV_NEXT))
        }
        return dayList
    }

    private fun getNextMonthDays(list: List<ScheduleDetailVo>) : List<CalendarDayVo>{
        val endDay = if(YearMonth.of(year, month).atEndOfMonth().dayOfWeek.value == 7) 0 else YearMonth.of(year, month).atEndOfMonth().dayOfWeek.value
        val firstDayOfNextMonth = YearMonth.of(year, month).plusMonths(1).atDay(1)
        val dayList = mutableListOf<CalendarDayVo>()
        (0 until (6 - endDay)).map {i ->
            val day = firstDayOfNextMonth.plusDays(i.toLong()).toString()
            val scheduleListForDay = list.filter { it.date == day }.sortedWith(compareBy({ it.recordType.priority }, { it.repeatTimes.first().time }))
            val isSunday = firstDayOfNextMonth.plusDays(i.toLong()).dayOfWeek == DayOfWeek.SUNDAY
            dayList.add(CalendarDayVo(day = TimeFormatter.getDay(day).toString(), scheduleList = scheduleListForDay, isSunday = isSunday, dayType = DayType.PREV_NEXT))
        }
        return dayList
    }

    fun onClickDay(){
        updateState(
            uiState.value.copy(
                isShowDetailDialog = true,
            )
        )
    }

    fun onClickDialogCancel(){
        updateState(
            uiState.value.copy(
                isShowDetailDialog = false,
            )
        )
    }
}