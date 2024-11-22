package com.hugg.list

import androidx.lifecycle.viewModelScope
import com.hugg.challenge.main.ChallengeMainEvent
import com.hugg.domain.base.StatusCode
import com.hugg.domain.model.response.challenge.ChallengeCardVo
import com.hugg.domain.repository.ChallengeRepository
import com.hugg.feature.base.BaseViewModel
import com.hugg.feature.util.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengeListViewModel @Inject constructor(
    private val challengeRepository: ChallengeRepository
) : BaseViewModel<ChallengeListPageState>(ChallengeListPageState()) {
    private val _showUnlockAnimationFlow: MutableSharedFlow<Boolean> = MutableSharedFlow()
    val showUnlockAnimationFlow = _showUnlockAnimationFlow.asSharedFlow()

    init {
        getAllChallenge()
    }

    fun onInputValueChange(newValue: String) {
        updateState(uiState.value.copy(searchKeyword = newValue))
    }

    fun getAllChallenge() {
        viewModelScope.launch {
            challengeRepository.getAllChallenge().collect {
                resultResponse(it, ::onSuccessGetAllChallenge)
            }
        }
    }

    private fun onSuccessGetAllChallenge(response: List<ChallengeCardVo>) {
        updateState(uiState.value.copy(challengeList = response))
        UserInfo.updateChallengePoint(response[0].point)
    }

    fun updateSelectedChallenge(item: ChallengeCardVo?) {
        updateState(uiState.value.copy(selectedChallenge = item))
    }

    fun unlockChallenge(id: Long) {
        viewModelScope.launch {
            challengeRepository.unlockChallenge(id).collect {
                resultResponse(it, { onSuccessUnlockChallenge() }, ::onFailedUnlockChallenge)
            }
        }
    }

    private fun onSuccessUnlockChallenge() {
        updateState(uiState.value.copy(selectedChallenge = uiState.value.selectedChallenge?.copy(open = true)))
        UserInfo.updateChallengePoint(UserInfo.challengePoint - 700)
        viewModelScope.launch {
            _showUnlockAnimationFlow.emit(true)
        }
    }

    private fun onFailedUnlockChallenge(error: String) {
        when (error) {
            StatusCode.CHALLENGE.INSUFFICIENT_POINTS -> {
                viewModelScope.launch {
                    _showUnlockAnimationFlow.emit(false)
                }
                emitEventFlow(ChallengeMainEvent.InsufficientPoint)
            }
        }
    }

    fun participateChallenge(id: Long) {
        viewModelScope.launch {
            challengeRepository.participateChallenge(id).collect {
                resultResponse(it, { updateState(uiState.value.copy(selectedChallenge = uiState.value.selectedChallenge?.copy(participating = true))) })
            }
        }
    }
}