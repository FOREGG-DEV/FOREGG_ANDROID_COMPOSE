package com.hugg.challenge.main

import androidx.lifecycle.viewModelScope
import com.hugg.domain.model.enums.ChallengeTabType
import com.hugg.domain.model.response.challenge.ChallengeCardVo
import com.hugg.domain.repository.ChallengeRepository
import com.hugg.feature.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengeMainViewModel @Inject constructor(
    private val challengeRepository: ChallengeRepository
) : BaseViewModel<ChallengeMainPageState>(ChallengeMainPageState()) {

    init {
        getChallengeList()
    }

    private fun getChallengeList() {
        viewModelScope.launch {
            challengeRepository.getAllChallenge().collect {
                resultResponse(it, ::onSuccessGetChallengeList)
            }
        }
    }

    private fun onSuccessGetChallengeList(response: List<ChallengeCardVo>) {
        updateState(
            uiState.value.copy(
                commonChallengeList = response
            )
        )
    }

    fun updateTabType(type: ChallengeTabType) {
        updateState(
            uiState.value.copy(
                currentTabType = type
            )
        )
    }

    fun createNickname(nickname: String) {
        updateShowDialog(true)
    }

    fun updateShowDialog(value: Boolean) {
        updateState(
            uiState.value.copy(
                showChallengeCompleteDialog = value
            )
        )
    }
}