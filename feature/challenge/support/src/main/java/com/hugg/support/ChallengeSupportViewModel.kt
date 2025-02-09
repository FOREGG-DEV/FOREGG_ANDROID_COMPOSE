package com.hugg.support

import androidx.lifecycle.viewModelScope
import com.hugg.domain.base.StatusCode
import com.hugg.domain.model.enums.CheerType
import com.hugg.domain.model.response.challenge.ChallengeSupportItemVo
import com.hugg.domain.model.response.challenge.ChallengeSupportListVo
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

    fun setChallengeId(challengeId: Long) {
        updateState(uiState.value.copy(challengeId = challengeId))
    }

    fun getChallengeSupportList(challengeId: Long, isSuccess: Boolean, page: Int, size: Int, isRefresh: Boolean = false) {
        viewModelScope.launch {
            challengeRepository.getChallengeSupportList(
                challengeId = challengeId,
                isSuccess = isSuccess,
                page = page,
                size = size
            ).collect {
                resultResponse(it, { onSuccessGetChallengeSupportList(it, isSuccess, isRefresh) }, ::onFailedGetChallengeSupportList)
            }
        }
    }

    private fun onSuccessGetChallengeSupportList(response: ChallengeSupportListVo, isSuccess: Boolean, isRefresh: Boolean) {
        when (isSuccess) {
            true -> {
                updateState(
                    uiState.value.copy(
                        completedList = if (isRefresh) response.items else uiState.value.completedList + response.items,
                        completedCurPage = response.currentPage,
                        completedTotalPage = response.totalPage
                    )
                )
            }
            false -> {
                updateState(
                    uiState.value.copy(
                        incompleteList = if (isRefresh) response.items else uiState.value.incompleteList + response.items,
                        incompleteCurPage = response.currentPage,
                        incompleteTotalPage = response.totalPage
                    )
                )
            }
        }
    }

    private fun onFailedGetChallengeSupportList(code: String) {
        when (code) {
            StatusCode.ETC.PAGE_LIMIT -> emitEventFlow(ChallengeSupportEvent.ExceedPageLimit)
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
                getChallengeSupportList(uiState.value.challengeId, isSuccess = true, 0, 5 * (uiState.value.completedCurPage + 1), isRefresh = true)
                emitEventFlow(ChallengeSupportEvent.ClapSuccess)
            }
            CheerType.SUPPORT -> {
                getChallengeSupportList(uiState.value.challengeId, isSuccess = false, 0, 5 * (uiState.value.incompleteCurPage + 1), isRefresh = true)
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