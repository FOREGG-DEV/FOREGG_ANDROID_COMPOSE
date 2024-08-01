package com.hugg.sign.femaleSignUp.surgeryCount

import com.hugg.feature.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SurgeryCountViewModel @Inject constructor() : BaseViewModel<SurgeryCountPageState>(
    SurgeryCountPageState()
) {

    companion object{
        const val MIN_COUNT = 0
        const val MAX_COUNT = 99
    }

    fun onClickPlusBtn(){
        if(uiState.value.count == MAX_COUNT) return
        updateState(uiState.value.copy(
            count = uiState.value.count + 1
        ))
    }

    fun onClickMinusBtn(){
        if(uiState.value.count == 0) return
        updateState(uiState.value.copy(
            count = uiState.value.count - 1
        ))
    }

    fun onClickNextBtn(){
        emitEventFlow(SurgeryCountEvent.GoToSurgeryStartDayPage)
    }
}