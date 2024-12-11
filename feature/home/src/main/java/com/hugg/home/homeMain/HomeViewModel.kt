package com.hugg.home.homeMain

import androidx.lifecycle.viewModelScope
import com.hugg.domain.model.enums.GenderType
import com.hugg.domain.model.enums.HomeChallengeViewType
import com.hugg.domain.model.enums.MyChallengeState
import com.hugg.domain.model.request.challenge.ChallengeThoughtsVo
import com.hugg.domain.model.response.challenge.MyChallengeListItemVo
import com.hugg.domain.model.response.challenge.MyChallengeVo
import com.hugg.domain.model.response.dailyHugg.DailyHuggListResponseVo
import com.hugg.domain.model.response.home.HomeRecordResponseVo
import com.hugg.domain.model.response.home.HomeResponseVo
import com.hugg.domain.model.response.profile.ProfileDetailResponseVo
import com.hugg.domain.model.vo.home.HomeTodayScheduleCardVo
import com.hugg.domain.repository.ChallengeRepository
import com.hugg.domain.repository.DailyHuggRepository
import com.hugg.domain.repository.HomeRepository
import com.hugg.domain.repository.ProfileRepository
import com.hugg.domain.repository.ScheduleRepository
import com.hugg.feature.base.BaseViewModel
import com.hugg.feature.util.ForeggLog
import com.hugg.feature.util.TimeFormatter
import com.hugg.feature.util.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val scheduleRepository: ScheduleRepository,
    private val challengeRepository: ChallengeRepository,
    private val dailyHuggRepository: DailyHuggRepository
) : BaseViewModel<HomePageState>(
    HomePageState()
) {
    init {
        getChallengeNicknameFromLocal()
    }

    fun getHome(){
        getTodayRecord()
        when(UserInfo.info.genderType){
            GenderType.MALE -> getDailyHuggList()
            GenderType.FEMALE -> getMyChallengeList()
        }
    }

    private fun getTodayRecord(){
        viewModelScope.launch {
            homeRepository.getHome().collect {
                resultResponse(it, ::handleInitScheduleStatesSuccess)
            }
        }
    }

    private fun getMyChallengeList(){
        viewModelScope.launch {
            challengeRepository.getMyChallenge().collect {
                resultResponse(it, ::onSuccessGetMyChallengeList)
            }
        }
    }

    private fun onSuccessGetMyChallengeList(response: MyChallengeVo) {
        updateState(
            uiState.value.copy(challengeList = getSortedByCompleteChallenge(response.dtos))
        )
    }

    private fun handleInitScheduleStatesSuccess(result: HomeResponseVo) {
        updateState(
            uiState.value.copy(
                todayScheduleList = splitListItem(result.homeRecordResponseVo)
            )
        )
    }

    private fun splitListItem(list: List<HomeRecordResponseVo>): List<HomeTodayScheduleCardVo> {
        return updateNearestTime(list.map {
            HomeTodayScheduleCardVo(
                id = it.id,
                recordType = it.recordType,
                time = it.time,
                name = it.name,
                memo = it.memo,
                todo = it.todo
            )
        }.sortedBy { it.time })
    }

    private fun updateNearestTime(scheduleList: List<HomeTodayScheduleCardVo>): List<HomeTodayScheduleCardVo> {
        val timeFormat = SimpleDateFormat("HH:mm", Locale.KOREA)
        val currentTimeString = timeFormat.format(Date())
        val currentTime = timeFormat.parse(currentTimeString)

        var nearestSchedule: HomeTodayScheduleCardVo? = null
        var minimumDifference = Long.MAX_VALUE

        for (schedule in scheduleList) {
            val scheduleTime = timeFormat.parse(schedule.time)
            val timeDifference = abs(currentTime.time - scheduleTime.time)

            if (timeDifference < minimumDifference) {
                minimumDifference = timeDifference
                nearestSchedule = schedule
            }
        }

        return scheduleList.map { schedule ->
            if (schedule == nearestSchedule) {
                schedule.copy(isNearlyNowTime = true)
            } else {
                schedule
            }
        }
    }

    private fun getSortedByCompleteChallenge(list: List<MyChallengeListItemVo>) : List<MyChallengeListItemVo>{
        val newList = list.map {
            it.copy(
                isCompleteToday = it.successDays?.any { it == TimeFormatter.getKoreanDayOfWeek(LocalDate.now().dayOfWeek) } == true
            )
        }

        return newList.sortedBy { it.isCompleteToday }
    }

    fun onClickTodo(id : Long, time : String) {
        updateClickTodoState(true)
        viewModelScope.launch {
            scheduleRepository.checkTodoRecord(id, time).collect {
                resultResponse(it, { getHome() })
            }
        }
    }

    fun updateClickTodoState(isClick : Boolean){
        updateState(uiState.value.copy(isClickTodo = isClick))
    }

    fun selectIncompleteChallenge(id: Long) {
        updateState(uiState.value.copy(selectedChallengeId = id))
        updateShowInputImpressionDialog(true)
    }

    fun completeChallenge(content : String){
        updateShowInputImpressionDialog(false)
        viewModelScope.launch {
            challengeRepository.completeChallenge(id = uiState.value.selectedChallengeId, day = MyChallengeState.TODAY.name, thoughts = ChallengeThoughtsVo(content)).collect {
                resultResponse(it, { updateShowChallengeCompleteDialog(true) })
            }
        }
    }

    fun updateShowInputImpressionDialog(isShow : Boolean){
        updateState(uiState.value.copy(showInputImpressionDialog = isShow))
    }

    fun updateShowChallengeCompleteDialog(isShow : Boolean){
        updateState(uiState.value.copy(showCompleteChallengeDialog = isShow))
    }

    private fun getDailyHuggList() {
        viewModelScope.launch {
            dailyHuggRepository.getDailyHuggList(0).collect {
                resultResponse(it, ::onSuccessGetDailyHuggList)
            }
        }
    }

    private fun onSuccessGetDailyHuggList(response: DailyHuggListResponseVo) {
        updateState(
            uiState.value.copy(
                dailyHuggList = response.dailyHuggList.take(3),
            )
        )
    }

    private fun getChallengeNicknameFromLocal() {
        viewModelScope.launch {
            challengeRepository.getNickname().collect { nickname ->
                if (nickname != null) {
                    UserInfo.updateChallengeNickname(nickname)
                } else {
                    getChallengeNicknameFromServer()
                }
            }
        }
    }

    private fun getChallengeNicknameFromServer() {
        viewModelScope.launch {
            challengeRepository.getChallengeName().collect {
                resultResponse(it, { UserInfo.updateChallengeNickname(it) })
            }
        }
    }
}