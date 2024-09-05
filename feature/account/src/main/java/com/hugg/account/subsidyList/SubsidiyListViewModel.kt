package com.hugg.account.subsidyList

import com.hugg.feature.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SubsidiyListViewModel @Inject constructor(
) : BaseViewModel<SubsidyListPageState>(
    SubsidyListPageState()
) {

    fun updateNowRound(round : Int){
        updateState(
            uiState.value.copy(nowRound = round)
        )
    }

}