package com.hugg.challenge.main

import androidx.lifecycle.viewModelScope
import com.hugg.domain.base.StatusCode
import com.hugg.domain.model.enums.ChallengeTabType
import com.hugg.domain.model.enums.MyChallengeState
import com.hugg.domain.model.request.challenge.ChallengeNicknameVo
import com.hugg.domain.model.request.challenge.ChallengeThoughtsVo
import com.hugg.domain.model.response.challenge.ChallengeCardVo
import com.hugg.domain.model.response.challenge.MyChallengeVo
import com.hugg.domain.model.response.profile.ProfileDetailResponseVo
import com.hugg.domain.repository.ChallengeRepository
import com.hugg.domain.repository.ProfileRepository
import com.hugg.feature.base.BaseViewModel
import com.hugg.feature.theme.CREATE_MY_CHALLENGE
import com.hugg.feature.theme.CREATE_MY_CHALLENGE_DESCRIPTION
import com.hugg.feature.util.TimeFormatter
import com.hugg.feature.util.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class ChallengeMainViewModel @Inject constructor(
    private val challengeRepository: ChallengeRepository,
    private val profileRepository: ProfileRepository
) : BaseViewModel<ChallengeMainPageState>(ChallengeMainPageState()) {
    private val _showUnlockAnimationFlow: MutableSharedFlow<Boolean> = MutableSharedFlow()
    val showUnlockAnimationFlow = _showUnlockAnimationFlow.asSharedFlow()

    companion object{
        const val TODAY_SUCCESS_POINT = 100
        const val YESTERDAY_SUCCESS_POINT = 50
    }

    fun getChallengeList() {
        viewModelScope.launch {
            challengeRepository.getAllCommonChallenge().collect {
                resultResponse(it, ::onSuccessGetChallengeList)
            }
        }
        getMyChallenge()
    }

    private fun onSuccessGetChallengeList(response: List<ChallengeCardVo>) {
        UserInfo.updateChallengePoint(response[0].point)
        val updatedList = addCreateChallengeCard(response)

        updateState(
            uiState.value.copy(
                challengePoint = UserInfo.challengePoint,
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
        when(type){
            ChallengeTabType.COMMON -> getChallengeList()
            ChallengeTabType.MY -> getMyChallenge()
        }
    }

    fun createNickname(nickname: String) {
        viewModelScope.launch {
            challengeRepository.joinChallenge(
                request = ChallengeNicknameVo(nickname)
            ).collect {
                resultResponse(it, { onSuccessCreateChallengeNickname(nickname) }, ::onFailedCreateChallengeNickname)
            }
        }
    }

    private fun onSuccessCreateChallengeNickname(nickname: String) {
        updateShowDialog(true)
        getMyInfo()
        getChallengeList()
    }

    private fun onFailedCreateChallengeNickname(e: String) {
        when (e) {
            StatusCode.CHALLENGE.DUPLICATE_NICKNAME -> {
                emitEventFlow(ChallengeMainEvent.DuplicateNickname)
            }
            StatusCode.CHALLENGE.EXIST_NICKNAME -> {
                emitEventFlow(ChallengeMainEvent.ExistNickname)
            }
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

    private fun getMyChallenge() {
        viewModelScope.launch {
            challengeRepository.getMyChallenge().collect {
                resultResponse(it, ::onSuccessGetMyChallenge)
            }
        }
    }

    private fun onSuccessGetMyChallenge(response: MyChallengeVo) {
        updateState(
            uiState.value.copy(
                myChallengeList = response.dtos
            )
        )
        emitEventFlow(ChallengeMainEvent.GetMyChallengeSuccess)
    }

    fun initializeChallengeState(currentPage: Int) {
        if (uiState.value.myChallengeList.isEmpty() || currentPage >= uiState.value.myChallengeList.size) return
        val challenge = uiState.value.myChallengeList[currentPage]
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val firstDate = LocalDate.parse(challenge.firstDate, formatter)
        val dayOfWeek = (0..6).associateBy { offset ->
            val date = firstDate.plusDays(offset.toLong())
            date.toString()
        }
        val today = TimeFormatter.getToday()
        val todayIndex = dayOfWeek.getOrDefault(today, -1)
        var successCount = 0

        if (todayIndex == -1) return

        val successDays = challenge.successDays
        val weekState = (0 until 7).map { dayIndex ->
            when {
                dayIndex < todayIndex -> {
                    if (dayOfWeek.entries.firstOrNull { it.value == dayIndex }?.key in successDays) {
                        successCount++
                        MyChallengeState.SUCCESS
                    } else if (dayIndex == todayIndex - 1) {
                        MyChallengeState.YESTERDAY
                    } else {
                        MyChallengeState.FAIL
                    }
                }
                dayIndex == todayIndex -> {
                    if (dayOfWeek.entries.firstOrNull { it.value == dayIndex }?.key in successDays) {
                        successCount++
                        MyChallengeState.SUCCESS
                    } else {
                        MyChallengeState.TODAY
                    }
                }
                else -> MyChallengeState.NOT_YET
            }
        }

        updateState(
            uiState.value.copy(
                currentChallengeState = weekState,
                currentChallengeDayOfWeek = todayIndex + 1,
                successCount = successCount,
                firstDateOfWeek = uiState.value.myChallengeList[currentPage].firstDate
            )
        )
    }

    fun updateCommentDialogVisibility(newValue: Boolean) {
        updateState(uiState.value.copy(showCommentDialog = newValue))
    }

    fun updateShowChallengeSuccessAnimation(newValue: Boolean) {
        updateState(uiState.value.copy(showChallengeSuccessAnimation = newValue))
    }

    fun updateDeleteDialogVisibility(newValue: Boolean) {
        updateState(uiState.value.copy(showDeleteDialog = newValue))
    }

    fun deleteChallenge(id: Long) {
        viewModelScope.launch {
            challengeRepository.deleteChallenge(id).collect {
                resultResponse(it, { getMyChallenge() })
            }
        }
    }

    fun completeChallenge(id: Long, state: MyChallengeState, thoughts: String) {
        val date = when (state) {
            MyChallengeState.YESTERDAY -> TimeFormatter.getYesterday()
            MyChallengeState.TODAY -> TimeFormatter.getToday()
            else -> ""
        }

        if (date.isEmpty()) return

        viewModelScope.launch {
            challengeRepository.completeChallenge(id = id, date = date, thoughts = ChallengeThoughtsVo(thoughts)).collect {
                resultResponse(it, { onSuccessCompleteChallenge(state) })
            }
        }
    }

    private fun onSuccessCompleteChallenge(state: MyChallengeState) {
        val point = if(state == MyChallengeState.TODAY) UserInfo.challengePoint + TODAY_SUCCESS_POINT else UserInfo.challengePoint + YESTERDAY_SUCCESS_POINT
        updateShowChallengeSuccessAnimation(true)
        updatePoint(point)
        getMyChallenge()
    }

    private fun updatePoint(point : Int){
        UserInfo.updateChallengePoint(point)
        updateState(uiState.value.copy(challengePoint = point))
    }

    private fun getMyInfo(){
        viewModelScope.launch {
            profileRepository.getMyInfo().collect{
                resultResponse(it, ::handleSuccessGetMyInfo)
            }
        }
    }

    private fun handleSuccessGetMyInfo(result : ProfileDetailResponseVo){
        UserInfo.updateInfo(result)
    }
}