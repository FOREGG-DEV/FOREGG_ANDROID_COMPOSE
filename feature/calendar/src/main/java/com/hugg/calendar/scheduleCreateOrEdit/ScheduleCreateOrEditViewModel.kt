package com.hugg.calendar.scheduleCreateOrEdit

import com.hugg.domain.model.enums.CreateOrEditType
import com.hugg.domain.model.enums.RecordType
import com.hugg.domain.model.vo.calendar.RepeatTimeVo
import com.hugg.feature.base.BaseViewModel
import com.hugg.feature.theme.CALENDAR_SCHEDULE_DEFAULT_TIME
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScheduleCreateOrEditViewModel @Inject constructor(
) : BaseViewModel<ScheduleCreateOrEditPageState>(
    ScheduleCreateOrEditPageState()
) {

    private var selectedIndex : Int = 0

    fun initView(pageType : CreateOrEditType, recordType: RecordType, id : Long){
        updateState(
            uiState.value.copy(pageType = pageType)
        )
        when(pageType){
            CreateOrEditType.CREATE -> updateRecordType(recordType)
            CreateOrEditType.EDIT -> {
                updateScheduleId(id)
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

    fun setRepeatTimeList(time : String){
        val newList = uiState.value.repeatTimeList.toMutableList()
        newList[selectedIndex] = newList[selectedIndex].copy(time = time)
        updateRepeatTimeList(newList)
    }

    fun onCheckedChange(checked : Boolean){
        updateState(
            uiState.value.copy(isAlarmCheck = checked)
        )
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
}