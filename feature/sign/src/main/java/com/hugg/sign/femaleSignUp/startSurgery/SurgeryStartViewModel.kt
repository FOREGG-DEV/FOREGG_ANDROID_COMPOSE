package com.hugg.sign.femaleSignUp.startSurgery

import com.hugg.feature.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SurgeryStartViewModel @Inject constructor() : BaseViewModel<SurgeryStartPageState>(
    SurgeryStartPageState()
) {

    fun selectStartDate(date : String){
        updateState(uiState.value.copy(
            date = date
        ))
    }

    fun onClickNextBtn(){
        emitEventFlow(SurgeryStartEvent.GoToSpouseCodePage)
    }
}