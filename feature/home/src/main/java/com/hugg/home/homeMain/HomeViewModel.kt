package com.hugg.home.homeMain

import androidx.lifecycle.viewModelScope
import com.hugg.domain.model.enums.GenderType
import com.hugg.domain.model.enums.HomeChallengeViewType
import com.hugg.domain.model.response.challenge.MyChallengeListItemVo
import com.hugg.domain.model.response.home.HomeRecordResponseVo
import com.hugg.domain.model.response.home.HomeResponseVo
import com.hugg.domain.model.response.profile.ProfileDetailResponseVo
import com.hugg.domain.model.vo.home.HomeTodayScheduleCardVo
import com.hugg.domain.repository.ChallengeRepository
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
    private val challengeRepository: ChallengeRepository
) : BaseViewModel<HomePageState>(
    HomePageState()
) {
    fun getHome(){
        getTodayRecord()
        when(UserInfo.info.genderType){
            GenderType.MALE -> {}
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
                resultResponse(it, ::handleSuccessGetMyChallengeList)
            }
        }
    }

    private fun handleInitScheduleStatesSuccess(result: HomeResponseVo) {
        updateState(
            uiState.value.copy(
                todayScheduleList = splitTodayScheduleByRepeatedTime(result.homeRecordResponseVo)
            )
        )
//        viewModelScope.launch {
//            dailyConditionTypeImageStateFlow.update { getDailyConditionTypeImage(result.dailyConditionType) }
//            dailyContentStateFlow.update { result.dailyContent }
//            medicalRecordStateFlow.update { result.latestMedicalRecord }
//            medicalRecordIdStateFlow.update { result.medicalRecordId }
//            if (UserInfo.info.genderType == GenderType.FEMALE) formattedTextStateFlow.update { resourceProvider.getString(R.string.today_schedule_format, userNameStateFlow.value, month, day) }
//            else formattedTextStateFlow.update { resourceProvider.getString(R.string.today_schedule_husband_format, userNameStateFlow.value, husbandNameStateFlow.value, month, day) }
//        }
    }

    private fun splitTodayScheduleByRepeatedTime(currentList: List<HomeRecordResponseVo>): List<HomeTodayScheduleCardVo> {
        val newList = mutableListOf<HomeTodayScheduleCardVo>()
        for(list in currentList) {
            val subList = splitListItem(list)
            newList.addAll(subList)
        }
        return updateNearestTime(newList.sortedBy { it.time })
    }

    private fun splitListItem(list: HomeRecordResponseVo): List<HomeTodayScheduleCardVo> {
        return list.times.map { repeatTime ->
            HomeTodayScheduleCardVo(
                id = list.id,
                recordType = list.recordType,
                time = repeatTime,
                name = list.name,
                memo = list.memo,
                todo = list.todo
            )
        }
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

    private fun handleSuccessGetMyChallengeList(list: List<MyChallengeListItemVo>) {
        val dummy = listOf<MyChallengeListItemVo>(
            MyChallengeListItemVo(
                id = -2,
                description = "대충 설명 1",
                image = "https://drive.google.com/uc?export=download&id=1ncLWFCTQHIC3Sthy79CitXgwmA3Jxf02",
                name = "대충 제목 입니다. 1"
            ),
            MyChallengeListItemVo(
                id = -2,
                description = "대충 설명 2",
                image = "https://drive.google.com/uc?export=download&id=1ncLWFCTQHIC3Sthy79CitXgwmA3Jxf02",
                name = "대충 제목 입니다. 2",
                successDays = listOf("화")
            ),
            MyChallengeListItemVo(
                id = -2,
                description = "대충 설명 3",
                image = "https://drive.google.com/uc?export=download&id=1ncLWFCTQHIC3Sthy79CitXgwmA3Jxf02",
                name = "대충 제목 입니다. 3",
            )
        )
        updateState(
            uiState.value.copy(challengeList = getSortedByCompleteChallenge(dummy))
        )
    }

    private fun getSortedByCompleteChallenge(list: List<MyChallengeListItemVo>) : List<MyChallengeListItemVo>{
        val newList = list.map {
            it.copy(
                isCompleteToday = it.successDays?.any { it == TimeFormatter.getKoreanDayOfWeek(LocalDate.now().dayOfWeek) } == true
            )
        }

        return newList.sortedBy { it.isCompleteToday }
    }

    fun onClickTodo(id : Long) {
        viewModelScope.launch {
            scheduleRepository.checkTodoRecord(id).collect {
                resultResponse(it, { getHome() })
            }
        }
    }

    fun selectIncompleteChallenge(id: Long) {
        updateState(uiState.value.copy(selectedChallengeId = id))
        updateShowInputImpressionDialog(true)
    }

    fun completeChallenge(content : String){
        ForeggLog.D(content)
        //        viewModelScope.launch {
//            challengeRepository.completeChallenge(id).collect {
//                resultResponse(it, { updateShowInputImpressionDialog(true) })
//            }
//        }
    }

    fun updateShowInputImpressionDialog(isShow : Boolean){
        updateState(uiState.value.copy(showInputImpressionDialog = isShow))
    }
}