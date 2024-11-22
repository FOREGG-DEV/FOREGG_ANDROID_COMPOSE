package com.hugg.challenge.main

import androidx.lifecycle.viewModelScope
import com.hugg.domain.base.StatusCode
import com.hugg.domain.model.enums.ChallengeTabType
import com.hugg.domain.model.request.challenge.ChallengeNicknameVo
import com.hugg.domain.model.response.challenge.ChallengeCardVo
import com.hugg.domain.repository.ChallengeRepository
import com.hugg.feature.base.BaseViewModel
import com.hugg.feature.theme.CREATE_MY_CHALLENGE
import com.hugg.feature.theme.CREATE_MY_CHALLENGE_DESCRIPTION
import com.hugg.feature.util.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengeMainViewModel @Inject constructor(
    private val challengeRepository: ChallengeRepository
) : BaseViewModel<ChallengeMainPageState>(ChallengeMainPageState()) {
    private val _showUnlockAnimationFlow: MutableSharedFlow<Boolean> = MutableSharedFlow()
    val showUnlockAnimationFlow = _showUnlockAnimationFlow.asSharedFlow()

    fun getChallengeList() {
        viewModelScope.launch {
            challengeRepository.getAllCommonChallenge().collect {
                resultResponse(it, ::onSuccessGetChallengeList)
            }
        }
    }

    private fun onSuccessGetChallengeList(response: List<ChallengeCardVo>) {
        UserInfo.updateChallengePoint(response[0].point)
        val updatedList = addCreateChallengeCard(response)

        updateState(
            uiState.value.copy(
                commonChallengeList = updatedList
            )
        )
    }

    private fun addCreateChallengeCard(challengeList: List<ChallengeCardVo>): List<ChallengeCardVo> {
        val newList = challengeList.filter { !it.isCreateChallenge }.toMutableList()
        val insertIndex = newList.indexOfLast { it.open }

        if (insertIndex == -1) {
            newList.add(
                ChallengeCardVo(
                    name = CREATE_MY_CHALLENGE,
                    description = CREATE_MY_CHALLENGE_DESCRIPTION,
                    open = true,
                    isCreateChallenge = true
                )
            )
        } else {
            newList.add(
                insertIndex + 1,
                ChallengeCardVo(
                    name = CREATE_MY_CHALLENGE,
                    description = CREATE_MY_CHALLENGE_DESCRIPTION,
                    open = true,
                    isCreateChallenge = true
                )
            )
        }

        return newList
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
        getChallengeList()
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

    fun unlockChallenge(id: Long) {
        viewModelScope.launch {
            challengeRepository.unlockChallenge(id).collect {
                resultResponse(it, { onSuccessUnlockChallenge() }, ::onFailedUnlockChallenge)
            }
        }
    }

    private fun onSuccessUnlockChallenge() {
        viewModelScope.launch {
            _showUnlockAnimationFlow.emit(true)
        }
        getChallengeList()
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
                resultResponse(it, { getChallengeList() }, ::onFailedParticipateChallenge)
            }
        }
    }

    private fun onFailedParticipateChallenge(error: String) {
        when (error) {
            StatusCode.CHALLENGE.ALREADY_PARTICIPATED -> emitEventFlow(ChallengeMainEvent.ChallengeAlreadyParticipated)
        }
    }
}