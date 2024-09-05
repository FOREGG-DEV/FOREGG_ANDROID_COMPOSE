package com.hugg.account.subsidyList

import androidx.lifecycle.viewModelScope
import com.hugg.domain.model.response.account.SubsidyListResponseVo
import com.hugg.domain.repository.AccountRepository
import com.hugg.feature.base.BaseViewModel
import com.hugg.feature.util.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubsidiyListViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : BaseViewModel<SubsidyListPageState>(
    SubsidyListPageState()
) {

    private var isRoundInitialized = false
    private var round = UserInfo.info.round

    fun initRound(round: Int){
        if(isRoundInitialized) return
        this.round = round
        updateNowRound()
        isRoundInitialized = true
    }

    private fun updateNowRound(){
        updateState(
            uiState.value.copy(nowRound = round)
        )
        setView()
    }

    fun onClickNextRound(){
        round++
        updateNowRound()
    }

    fun onClickPrevRound(){
        if(round == 0) return
        round--
        updateNowRound()
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