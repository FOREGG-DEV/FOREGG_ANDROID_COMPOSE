package com.hugg.list

import androidx.lifecycle.viewModelScope
import com.hugg.domain.model.response.challenge.AllChallengeItemVo
import com.hugg.domain.repository.ChallengeRepository
import com.hugg.feature.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengeListViewModel @Inject constructor(
    private val challengeRepository: ChallengeRepository
) : BaseViewModel<ChallengeListPageState>(ChallengeListPageState()) {

    init {
        getAllChallenge()
    }

    fun onInputValueChange(newValue: String) {
        updateState(uiState.value.copy(searchKeyword = newValue))
    }

    private fun getAllChallenge() {
        viewModelScope.launch {
            challengeRepository.getAllChallenge().collect {
                resultResponse(it, ::onSuccessGetAllChallenge)
            }
        }
    }

    private fun onSuccessGetAllChallenge(response: List<AllChallengeItemVo>) {
        updateState(uiState.value.copy(challengeList = response))
    }
}