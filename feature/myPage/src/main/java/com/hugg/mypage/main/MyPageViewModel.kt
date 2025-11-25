package com.hugg.mypage.main

import androidx.lifecycle.viewModelScope
import com.hugg.domain.model.response.profile.ProfileDetailResponseVo
import com.hugg.domain.repository.ForeggJwtRepository
import com.hugg.domain.repository.ProfileRepository
import com.hugg.feature.base.BaseViewModel
import com.hugg.feature.util.ForeggLog
import com.hugg.feature.util.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val foreggJwtRepository: ForeggJwtRepository
) : BaseViewModel<MyPagePageState>(
    MyPagePageState()
) {

    fun getMyInfo(){
        viewModelScope.launch {
            profileRepository.getMyInfo().collect{
                resultResponse(it, ::handleSuccessGetMyInfo)
            }
        }

        viewModelScope.launch {
            foreggJwtRepository.getAlarmSetting().collectLatest {
                updateState(
                    uiState.value.copy(alarmSetting = it)
                )
            }
        }
    }

    fun alarmSetting(checked: Boolean) {
        viewModelScope.launch {
            foreggJwtRepository.setAlarmSetting(checked).collect {
                updateState(
                    uiState.value.copy(alarmSetting = checked)
                )
                ForeggLog.D("알람 상황 $checked")
            }
        }
    }

    private fun handleSuccessGetMyInfo(result : ProfileDetailResponseVo){
        updateState(
            uiState.value.copy(
                spouse = result.spouse
            )
        )
        UserInfo.updateInfo(result)
    }
}