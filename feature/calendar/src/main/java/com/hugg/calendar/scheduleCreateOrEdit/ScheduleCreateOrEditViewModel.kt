package com.hugg.calendar.scheduleCreateOrEdit

import com.hugg.domain.model.enums.CreateOrEditType
import com.hugg.domain.model.enums.RecordType
import com.hugg.feature.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScheduleCreateOrEditViewModel @Inject constructor(
) : BaseViewModel<ScheduleCreateOrEditPageState>(
    ScheduleCreateOrEditPageState()
) {

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