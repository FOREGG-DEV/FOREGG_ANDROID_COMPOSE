package com.hugg.mypage.spouse

import androidx.lifecycle.viewModelScope
import com.hugg.domain.model.response.profile.ProfileDetailResponseVo
import com.hugg.domain.repository.ProfileRepository
import com.hugg.feature.base.BaseViewModel
import com.hugg.feature.util.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageSpouseViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : BaseViewModel<MyPageSpousePageState>(
    MyPageSpousePageState()
) {

    fun getMyInfo(){
        viewModelScope.launch {
            profileRepository.getMyInfo().collect{
                resultResponse(it, ::handleSuccessGetMyInfo)
            }
        }
    }

    private fun handleSuccessGetMyInfo(result : ProfileDetailResponseVo){
        updateState(
            uiState.value.copy(spouseCode = result.shareCode)
        )
        UserInfo.updateInfo(result)
    }

    fun onClickCopyBtn(){
        viewModelScope.launch {
            updateState(uiState.value.copy(
                isShowSnackBar = true
            ))
            delay(1000)
            updateState(uiState.value.copy(
                isShowSnackBar = false
            ))
        }
    }
}