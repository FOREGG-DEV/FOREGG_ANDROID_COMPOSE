package com.hugg.sign.serviceTerms

import com.hugg.feature.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ServiceTermsViewModel @Inject constructor() : BaseViewModel<ServiceTermsPageState>(
    ServiceTermsPageState()
) {

    fun updateAllTerm(){
        if(uiState.value.isAllTermChecked){
            updateState(
                uiState.value.copy(
                    isServiceTermChecked = false,
                    isPersonalTermChecked = false,
                    isAgeTermChecked = false
                )
            )
        }
        else{
            updateState(
                uiState.value.copy(
                    isServiceTermChecked = true,
                    isPersonalTermChecked = true,
                    isAgeTermChecked = true
                )
            )
        }
    }
    fun updateServiceTerm(){
        updateState(uiState.value.copy(isServiceTermChecked = !uiState.value.isServiceTermChecked))
    }

    fun updatePersonalTerm(){
        updateState(uiState.value.copy(isPersonalTermChecked = !uiState.value.isPersonalTermChecked))
    }

    fun updateAgeTerm(){
        updateState(uiState.value.copy(isAgeTermChecked = !uiState.value.isAgeTermChecked))
    }
}