package com.hugg.mypage.profileManagement

import androidx.lifecycle.viewModelScope
import com.hugg.domain.repository.ProfileRepository
import com.hugg.feature.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageProfileManagementViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : BaseViewModel<MyPageProfileManagementPageState>(
    MyPageProfileManagementPageState()
) {
    fun showLogoutDialog(flag : Boolean){
        updateState(
            uiState.value.copy(isShowLogoutDialog = flag)
        )
    }

    fun showUnregisterDialog(flag : Boolean){
        updateState(
            uiState.value.copy(isShowUnregisterDialog = flag)
        )
    }

    fun showLogoutCompleteDialog(flag : Boolean){
        updateState(
            uiState.value.copy(isShowLogoutCompleteDialog = flag)
        )
    }

    fun showUnregisterCompleteDialog(flag : Boolean){
        updateState(
            uiState.value.copy(isShowUnregisterCompleteDialog = flag)
        )
    }

    fun logout(){
        viewModelScope.launch {
            profileRepository.logout().collect{
                resultResponse(it, { emitEventFlow(MyPageProfileManagementEvent.SuccessLogoutEvent) })
            }
        }
    }

    fun unregister(){
        viewModelScope.launch {
            profileRepository.unRegister().collect{
                resultResponse(it, { emitEventFlow(MyPageProfileManagementEvent.SuccessUnregisterEvent) })
            }
        }
    }
}