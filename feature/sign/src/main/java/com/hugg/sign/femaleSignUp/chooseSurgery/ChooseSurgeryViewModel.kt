package com.hugg.sign.femaleSignUp.chooseSurgery

import com.hugg.domain.model.enums.SurgeryType
import com.hugg.feature.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChooseSurgeryViewModel @Inject constructor() : BaseViewModel<ChooseSurgeryPageState>(
    ChooseSurgeryPageState()
) {

    fun onClickDropDown(){
        updateState(uiState.value.copy(
            isExpandMenu = !uiState.value.isExpandMenu
        ))
    }

    fun onClickType(type : SurgeryType){
        updateState(uiState.value.copy(
            surgeryType = type,
            isExpandMenu = false
        ))
    }

    fun onClickNextBtn(){
        if(uiState.value.surgeryType == SurgeryType.THINK_SURGERY) emitEventFlow(ChooseSurgeryEvent.GoToSpouseCodePage)
        else emitEventFlow(ChooseSurgeryEvent.GoToSpouseCodePage)
    }
}