package com.hugg.calendar.scheduleCreateOrEdit

import androidx.lifecycle.viewModelScope
import com.hugg.domain.model.enums.CreateOrEditType
import com.hugg.domain.model.enums.RecordType
import com.hugg.domain.model.enums.RepeatDayType
import com.hugg.domain.model.request.calendar.ScheduleDetailRequestVo
import com.hugg.domain.model.vo.calendar.RepeatTimeVo
import com.hugg.domain.model.vo.calendar.ScheduleDetailVo
import com.hugg.domain.repository.ScheduleRepository
import com.hugg.feature.base.BaseViewModel
import com.hugg.feature.theme.CALENDAR_SCHEDULE_DEFAULT_TIME
import com.hugg.feature.theme.WORD_EVERYDAY
import com.hugg.feature.util.TimeFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleCreateOrEditViewModel @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) : BaseViewModel<ScheduleCreateOrEditPageState>(
    ScheduleCreateOrEditPageState()
) {

    private var selectedIndex : Int = 0
    private var selectedDate : RepeatDayType = RepeatDayType.NORMAL

    fun initView(pageType : CreateOrEditType, recordType: RecordType, id : Long, selectDate : String){
        updateState(
            uiState.value.copy(
                pageType = pageType,
            )
        )
        when(pageType){
            CreateOrEditType.CREATE -> {
                updateNormalDate(selectDate)
                updateRecordType(recordType)
            }
            CreateOrEditType.EDIT -> {
                updateScheduleId(id)
                getCalendarDetail(id)
            }
        }
    }

    fun showOrCancelDropDown(){
        updateState(
            uiState.value.copy(isExpandDropDown = !uiState.value.isExpandDropDown)
        )
    }

    fun onChangedName(name : String){
        updateState(
            uiState.value.copy(name = name)
        )
    }

    fun onChangedDose(dose : String){
        updateState(
            uiState.value.copy(dose = dose)
        )
    }

    fun onChangedMemo(memo : String){
        updateState(
            uiState.value.copy(memo = memo)
        )
    }

    fun onClickMinusBtn(){
        if(uiState.value.repeatCount == 1) return
        updateRepeatCount(uiState.value.repeatCount - 1)
        val updatedList = uiState.value.repeatTimeList.toMutableList().apply { removeLast() }
        updateRepeatTimeList(updatedList)
    }

    fun onClickPlusBtn(){
        updateRepeatCount(uiState.value.repeatCount + 1)
        updateRepeatTimeList(uiState.value.repeatTimeList + listOf(RepeatTimeVo(CALENDAR_SCHEDULE_DEFAULT_TIME)))
    }

    fun setTouchedTimePicker(index : Int){
        selectedIndex = index
    }

    fun setTouchedDatePicker(type : RepeatDayType){
        selectedDate = type
    }

    fun setRepeatTimeList(time : String){
        val newList = uiState.value.repeatTimeList.toMutableList()
        newList[selectedIndex] = newList[selectedIndex].copy(time = time)
        updateRepeatTimeList(newList)
    }

    fun setDate(date : String){
        when(selectedDate){
            RepeatDayType.NORMAL -> updateNormalDate(date)
            RepeatDayType.START -> updateStartDate(date)
            RepeatDayType.END -> updateEndDate(date)
        }
    }

    private fun updateNormalDate(date : String){
        updateState(uiState.value.copy(date = date))
    }

    private fun updateStartDate(date : String){
        updateState(uiState.value.copy(startDate = date))
    }

    private fun updateEndDate(date : String){
        updateState(uiState.value.copy(endDate = date))
    }

    fun onCheckedChange(checked : Boolean){
        updateState(
            uiState.value.copy(isAlarmCheck = checked)
        )
    }

    fun onRepeatChange(checked : Boolean){
        updateState(
            uiState.value.copy(isRepeatDay = checked)
        )
    }

    fun onClickCreateOrEdit() {
        when(uiState.value.pageType){
            CreateOrEditType.CREATE -> createSchedule()
            CreateOrEditType.EDIT -> editSchedule()
        }
    }

    fun onDeleteSchedule(){
        viewModelScope.launch {
            scheduleRepository.deleteSchedule(uiState.value.id).collect {
                resultResponse(it, { emitEventFlow(ScheduleCreateOrEditEvent.SuccessDeleteScheduleEvent) })
            }
        }
    }

    private fun createSchedule(){
        val request = getCreateScheduleRequest()
        viewModelScope.launch {
            scheduleRepository.addSchedule(request).collect {
                resultResponse(it, { emitEventFlow(ScheduleCreateOrEditEvent.SuccessCreateScheduleEvent) })
            }
        }
    }

    private fun getCreateScheduleRequest() : ScheduleDetailRequestVo {
        return ScheduleDetailRequestVo(
            recordType = uiState.value.recordType,
            name = uiState.value.name,
            date = if(uiState.value.isRepeatDay) null else uiState.value.date,
            startDate = if(uiState.value.isRepeatDay) uiState.value.startDate else null,
            endDate = if(uiState.value.isRepeatDay) uiState.value.endDate else null,
            repeatDate = if(uiState.value.isRepeatDay) WORD_EVERYDAY else null,
            repeatTimes = getTimeList(),
            vibration = uiState.value.isAlarmCheck,
            dose = uiState.value.dose.ifEmpty { null },
            memo = uiState.value.memo
        )
    }

    private fun getTimeList() : List<RepeatTimeVo> {
        return uiState.value.repeatTimeList.map {
            RepeatTimeVo(TimeFormatter.formatKorTimeToNormalTime(it.time))
        }
    }

    private fun editSchedule(){
        val request = getCreateScheduleRequest()
        viewModelScope.launch {
            scheduleRepository.modifySchedule(uiState.value.id, request).collect {
                resultResponse(it, { emitEventFlow(ScheduleCreateOrEditEvent.SuccessModifyScheduleEvent) })
            }
        }
    }

    private fun updateRepeatCount(count : Int){
        updateState(
            uiState.value.copy(repeatCount = count)
        )
    }

    private fun updateRepeatTimeList(list : List<RepeatTimeVo>){
        updateState(
            uiState.value.copy(repeatTimeList = list)
        )
    }

    private fun updateRecordType(type : RecordType){
        updateState(
            uiState.value.copy(recordType = type)
        )
    }

    private fun updateScheduleId(id : Long){
        updateState(
            uiState.value.copy(id = id)
        )
    }

    private fun getCalendarDetail(id : Long){
        viewModelScope.launch {
            scheduleRepository.getDetailRecord(id).collect {
                resultResponse(it, ::handleSuccessGetDetailSchedule)
            }
        }
    }

    private fun handleSuccessGetDetailSchedule(result : ScheduleDetailVo){
        updateRecordType(result.recordType)
        onChangedName(result.name)
        result.dose?.let { onChangedDose(it) }
        updateRepeatCount(result.repeatTimes.size)
        updateRepeatTimeList(result.repeatTimes)
        onCheckedChange(result.vibration)
        result.date?.let { updateNormalDate(it) }
        result.startDate?.let { updateStartDate(it) }
        result.endDate?.let { updateEndDate(it) }
        onChangedMemo(result.memo)
        result.repeatDate?.let { onRepeatChange(it.isNotEmpty()) }
    }

    fun showDeleteDialog(isShow : Boolean){
        updateState(
            uiState.value.copy(showDeleteDialog = isShow)
        )
    }
}