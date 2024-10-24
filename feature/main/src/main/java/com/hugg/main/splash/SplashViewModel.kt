package com.hugg.main.splash

import androidx.lifecycle.viewModelScope
import com.hugg.domain.model.response.profile.ProfileDetailResponseVo
import com.hugg.domain.repository.ProfileRepository
import com.hugg.feature.base.BaseViewModel
import com.hugg.feature.base.PageState
import com.hugg.feature.util.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : BaseViewModel<PageState.Default>(PageState.Default) {

    fun checkLogin(){
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