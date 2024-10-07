package com.hugg.home.homeMain

import androidx.lifecycle.viewModelScope
import com.hugg.domain.model.enums.GenderType
import com.hugg.domain.model.response.home.HomeRecordResponseVo
import com.hugg.domain.model.response.home.HomeResponseVo
import com.hugg.domain.model.response.profile.ProfileDetailResponseVo
import com.hugg.domain.model.vo.home.HomeTodayScheduleCardVo
import com.hugg.domain.repository.HomeRepository
import com.hugg.domain.repository.ProfileRepository
import com.hugg.feature.base.BaseViewModel
import com.hugg.feature.util.ForeggLog
import com.hugg.feature.util.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : BaseViewModel<HomePageState>(
    HomePageState()
) {
    fun getHome(){
        viewModelScope.launch {
            homeRepository.getHome().collect {
                resultResponse(it, ::handleInitScheduleStatesSuccess)
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

}