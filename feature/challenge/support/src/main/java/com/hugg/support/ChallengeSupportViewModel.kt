package com.hugg.support

import androidx.lifecycle.viewModelScope
import com.hugg.domain.base.StatusCode
import com.hugg.domain.model.enums.CheerType
import com.hugg.domain.model.response.challenge.ChallengeSupportItemVo
import com.hugg.domain.model.response.challenge.ChallengeSupportResponseVo
import com.hugg.domain.repository.ChallengeRepository
import com.hugg.feature.base.BaseViewModel
import com.hugg.feature.util.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengeSupportViewModel @Inject constructor(
    private val challengeRepository: ChallengeRepository
) : BaseViewModel<ChallengeSupportPageState>(ChallengeSupportPageState()) {

    companion object{
        const val INITIAL_PAGE = 0
    }

    fun setChallengeId(challengeId: Long) {
        updateState(uiState.value.copy(challengeId = challengeId))
    }

    fun getChallengeSupportList(challengeId: Long, isSuccess: Boolean) {
        viewModelScope.launch {
            challengeRepository.getChallengeSupportList(
                challengeId = challengeId,
                isSuccess = isSuccess,
                page = INITIAL_PAGE.toLong()
            ).collect { it ->
                resultResponse(it, { onSuccessGetChallengeSupportList(it, isSuccess) })
            }
        }
    }

    fun loadMoreChallengeSupportList(challengeId: Long, isSuccess: Boolean) {
        val nextPage = if(isSuccess) uiState.value.completedList.currentPage + 1 else uiState.value.incompleteList.currentPage + 1
        viewModelScope.launch {
            challengeRepository.getChallengeSupportList(
                challengeId = challengeId,
                isSuccess = isSuccess,
                page = nextPage.toLong(),
            ).collect { it ->
                resultResponse(it, { onSuccessLoadChallengeSupportList(it, isSuccess) })
            }
        }
    }

    private fun onSuccessGetChallengeSupportList(response: ChallengeSupportResponseVo, isSuccess: Boolean) {
        when(isSuccess) {
            true -> {
                updateState(uiState.value.copy(completedList = response))
            }
            false -> {
                updateState(uiState.value.copy(incompleteList = response))
            }
        }
    }

    private fun onSuccessLoadChallengeSupportList(response: ChallengeSupportResponseVo, isSuccess: Boolean) {
        when(isSuccess) {
            true -> {
                updateState(uiState.value.copy(completedList = response.copy(
                    item = uiState.value.completedList.item + response.item
                )))
            }
            false -> {
                updateState(uiState.value.copy(incompleteList = response.copy(
                    item = uiState.value.incompleteList.item + response.item
                )))
            }
        }
    }

    fun supportChallenge(challengeId: Long, userId: Long, cheerType: CheerType) {
        viewModelScope.launch {
            challengeRepository.supportChallenge(
                challengeId = challengeId,
                userId = userId,
                cheerType = cheerType
            ).collect {
                resultResponse(it, { onSuccessSupportChallenge(cheerType) }, ::onFailedSupportChallenge)
            }
        }
    }

    private fun onSuccessSupportChallenge(cheerType: CheerType) {
        when(cheerType) {
            CheerType.CLAP -> {
                getChallengeSupportList(uiState.value.challengeId, isSuccess = true)
                emitEventFlow(ChallengeSupportEvent.ClapSuccess)
            }
            CheerType.SUPPORT -> {
                getChallengeSupportList(uiState.value.challengeId, isSuccess = false)
                emitEventFlow(ChallengeSupportEvent.SupportSuccess)
            }
            CheerType.REPLY -> return
        }
    }

    private fun onFailedSupportChallenge(code: String) {
        when(code) {
            StatusCode.CHALLENGE.SUPPORT_LIMIT -> { emitEventFlow(ChallengeSupportEvent.ExceedSupportLimit) }
        }
    }
}