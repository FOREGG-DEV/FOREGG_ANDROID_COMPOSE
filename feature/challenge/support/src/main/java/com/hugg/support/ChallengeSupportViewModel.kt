package com.hugg.support

import androidx.lifecycle.viewModelScope
import com.hugg.domain.model.response.challenge.ChallengeSupportItemVo
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

    fun getChallengeSupportList(challengeId: Long, isSuccess: Boolean) {
        viewModelScope.launch {
            challengeRepository.getChallengeSupportList(
                challengeId = challengeId,
                isSuccess = isSuccess
            ).collect {
                resultResponse(it, { onSuccessGetChallengeSupportList(it, isSuccess) })
            }
        }
    }

    private fun onSuccessGetChallengeSupportList(response: List<ChallengeSupportItemVo>, isSuccess: Boolean) {
        when(isSuccess) {
            true -> {
                updateState(uiState.value.copy(completedList = response))
            }
            false -> {
                updateState(uiState.value.copy(incompleteList = response))
            }
        }
    }
}