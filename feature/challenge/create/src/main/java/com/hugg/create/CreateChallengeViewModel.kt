package com.hugg.create

import androidx.lifecycle.viewModelScope
import com.hugg.domain.base.StatusCode
import com.hugg.domain.model.request.challenge.CreateChallengeRequestVo
import com.hugg.domain.repository.ChallengeRepository
import com.hugg.feature.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateChallengeViewModel @Inject constructor(
    private val challengeRepository: ChallengeRepository
) : BaseViewModel<CreateChallengePageState>(CreateChallengePageState()) {

    fun createChallenge() {
        viewModelScope.launch {
            challengeRepository.createChallenge(
                request = CreateChallengeRequestVo(
                    name = uiState.value.challengeName,
                    description = uiState.value.challengeDescription,
                    challengeEmojiType = uiState.value.emojiList[uiState.value.selectedEmojiIndex].second
                )
            ).collect {
                resultResponse(it, { emitEventFlow(CreateChallengeEvent.CreateChallengeCompleted) }, ::handleCreateChallengeError)
            }
        }
    }

    private fun handleCreateChallengeError(error: String) {
        when(error) {
            StatusCode.CHALLENGE.INSUFFICIENT_POINTS -> emitEventFlow(CreateChallengeEvent.InSufficientPoint)
        }
    }

    fun onNameChanged(newValue: String) {
        updateState(
            uiState.value.copy(
                challengeName = if (newValue.length <= 13) newValue else newValue.take(13)
            )
        )
    }

    fun onDescriptionChanged(newValue: String) {
        updateState(
            uiState.value.copy(
                challengeDescription = if (newValue.length <= 60) newValue else newValue.take(60)
            )
        )
    }

    fun updateEmojiVisibility(newValue: Boolean) {
        updateState(uiState.value.copy(showEmojiList = newValue))
    }

    fun updateSelectedEmojiIndex(newIndex: Int) {
        updateState(uiState.value.copy(selectedEmojiIndex = newIndex))
    }
}