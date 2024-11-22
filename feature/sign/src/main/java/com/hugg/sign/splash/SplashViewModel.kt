package com.hugg.sign.splash

import androidx.lifecycle.viewModelScope
import com.hugg.domain.model.response.profile.ProfileDetailResponseVo
import com.hugg.domain.repository.ChallengeRepository
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
    private val profileRepository: ProfileRepository,
    private val challengeRepository: ChallengeRepository
) : BaseViewModel<PageState.Default>(PageState.Default) {

    init {
        getChallengeNicknameFromLocal()
    }

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

    private fun getChallengeNicknameFromLocal() {
        viewModelScope.launch {
            challengeRepository.getNickname().collect { nickname ->
                if (nickname != null) {
                    UserInfo.updateChallengeNickname(nickname)
                } else {
//                    getChallengeNicknameFromServer()
                }
            }
        }
    }

    private fun getChallengeNicknameFromServer() {
        viewModelScope.launch {
            // TODO 서버에서 챌린지 닉네임 가져오는 로직
        }
    }
}