package com.hugg.challenge.main

import androidx.lifecycle.viewModelScope
import com.hugg.domain.base.StatusCode
import com.hugg.domain.model.enums.ChallengeTabType
import com.hugg.domain.model.request.challenge.ChallengeNicknameVo
import com.hugg.domain.model.response.challenge.ChallengeCardVo
import com.hugg.domain.repository.ChallengeRepository
import com.hugg.feature.base.BaseViewModel
import com.hugg.feature.util.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
        UserInfo.updateChallengePoint(response[0].point)
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
        viewModelScope.launch {
            challengeRepository.joinChallenge(
                request = ChallengeNicknameVo(nickname)
            ).collect {
                resultResponse(it, { onSuccessCreateChallenge(nickname) }, ::onFailedCreateChallenge)
            }
        }
    }

    private fun onSuccessCreateChallenge(nickname: String) {
        updateShowDialog(true)
        UserInfo.updateChallengeNickname(nickname)
        saveChallengeNickname(nickname)
    }

    private fun onFailedCreateChallenge(e: String) {
        when (e) {
            StatusCode.CHALLENGE.DUPLICATE_NICKNAME -> {
                emitEventFlow(ChallengeMainEvent.DuplicateNickname)
            }
            StatusCode.CHALLENGE.EXIST_NICKNAME -> {
                emitEventFlow(ChallengeMainEvent.ExistNickname)
            }
        }
    }

    private fun saveChallengeNickname(nickname: String) {
        viewModelScope.launch(Dispatchers.IO) {
            challengeRepository.saveNickname(nickname)
        }
    }

    fun updateShowDialog(value: Boolean) {
        updateState(
            uiState.value.copy(
                showChallengeCompleteDialog = value
            )
        )
    }
}