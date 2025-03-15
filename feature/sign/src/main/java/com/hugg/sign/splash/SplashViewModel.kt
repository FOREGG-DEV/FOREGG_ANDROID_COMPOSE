package com.hugg.sign.splash

import androidx.lifecycle.viewModelScope
import com.hugg.domain.model.response.profile.ProfileDetailResponseVo
import com.hugg.domain.repository.AuthRepository
import com.hugg.domain.repository.ProfileRepository
import com.hugg.feature.base.BaseViewModel
import com.hugg.feature.base.PageState
import com.hugg.feature.util.ForeggLog
import com.hugg.feature.util.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val authRepository: AuthRepository,
) : BaseViewModel<PageState.Default>(PageState.Default) {

    fun getVersion(){
        viewModelScope.launch {
            authRepository.getAppVersion().collect{
                resultResponse(it, ::handleSuccessGetVersion)
            }
        }
    }

    private fun handleSuccessGetVersion(version : String){
        if(version == UserInfo.VERSION) checkLogin()
        else emitEventFlow(SplashEvent.GoToUpdateEvent)
    }

    private fun checkLogin(){
        viewModelScope.launch {
            delay(1000)
            profileRepository.getMyInfo().collect{
                resultResponse(it, ::handleSuccessGetMyInfo, { goToSign() }, false)
            }
        }
    }

    private fun handleSuccessGetMyInfo(result : ProfileDetailResponseVo){
        UserInfo.updateInfo(result)
        goToMain()
    }

    private fun goToMain(){
        emitEventFlow(SplashEvent.GoToMainEvent)
    }

    private fun goToSign(){
        emitEventFlow(SplashEvent.GoToSignEvent)
    }
}