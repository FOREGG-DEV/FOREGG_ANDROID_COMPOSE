package com.hugg.home.notification

import androidx.lifecycle.viewModelScope
import com.hugg.domain.model.enums.GenderType
import com.hugg.domain.model.response.challenge.MyChallengeListItemVo
import com.hugg.domain.model.response.dailyHugg.DailyHuggListResponseVo
import com.hugg.domain.model.response.home.HomeRecordResponseVo
import com.hugg.domain.model.response.home.HomeResponseVo
import com.hugg.domain.model.response.notification.NotificationHistoryResponseVo
import com.hugg.domain.model.vo.home.HomeTodayScheduleCardVo
import com.hugg.domain.repository.ChallengeRepository
import com.hugg.domain.repository.DailyHuggRepository
import com.hugg.domain.repository.HomeRepository
import com.hugg.domain.repository.ScheduleRepository
import com.hugg.feature.base.BaseViewModel
import com.hugg.feature.util.TimeFormatter
import com.hugg.feature.util.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
) : BaseViewModel<NotificationPageState>(
    NotificationPageState()
) {

    companion object{
        const val INITIAL_PAGE = 0
    }

    init {
        getNotificationHistory(INITIAL_PAGE)
    }

    private fun getNotificationHistory(page : Int){
        viewModelScope.launch {
            homeRepository.getNotificationHistory(page.toLong()).collect {
                resultResponse(it, ::handleSuccessGetNotificationList)
            }
        }
    }

    private fun handleSuccessGetNotificationList(result : NotificationHistoryResponseVo){
        updateState(
            uiState.value.copy(
                notificationList = uiState.value.notificationList + result.itemList,
                currentPage = result.currentPage,
                totalPage = result.totalPage
            )
        )
    }

    fun getNextPageNotificationList(){
        if(uiState.value.currentPage >= uiState.value.totalPage) return
        getNotificationHistory(uiState.value.currentPage + 1)
    }
}