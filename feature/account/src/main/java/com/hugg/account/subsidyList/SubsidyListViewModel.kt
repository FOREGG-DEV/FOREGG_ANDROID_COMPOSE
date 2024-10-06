package com.hugg.account.subsidyList

import androidx.lifecycle.viewModelScope
import com.hugg.domain.model.response.account.SubsidyListResponseVo
import com.hugg.domain.repository.AccountRepository
import com.hugg.feature.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubsidyListViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : BaseViewModel<SubsidyListPageState>(
    SubsidyListPageState()
) {

    private var isRoundInitialized = false

    fun initRound(round: Int){
        if(isRoundInitialized) {
            setView()
        }
        else{
            updateNowRound(round)
            isRoundInitialized = true
        }
    }

    private fun updateNowRound(round : Int){
        updateState(
            uiState.value.copy(nowRound = round)
        )
        setView()
    }

    fun onClickNextRound(){
        updateNowRound(uiState.value.nowRound + 1)
    }

    fun onClickPrevRound(){
        if(uiState.value.nowRound == 0) return
        updateNowRound(uiState.value.nowRound - 1)
    }

    private fun setView(){
        viewModelScope.launch {
            accountRepository.getSubsidies(uiState.value.nowRound).collect {
                resultResponse(it, ::handleGetSuccessSubsidies)
            }
        }
    }

    private fun handleGetSuccessSubsidies(result : List<SubsidyListResponseVo>){
        updateState(
            uiState.value.copy(subsidyList = result)
        )
    }

}