package com.hugg.sign.onboarding

import androidx.lifecycle.viewModelScope
import com.google.firebase.messaging.FirebaseMessaging
import com.hugg.data.base.StatusCode
import com.hugg.domain.model.request.fcm.RenewalFcmRequestVo
import com.hugg.domain.model.request.sign.SaveForeggJwtRequestVo
import com.hugg.domain.model.response.profile.ProfileDetailResponseVo
import com.hugg.domain.model.response.sign.SignResponseVo
import com.hugg.domain.model.vo.onboarding.OnboardingTutorialVo
import com.hugg.domain.model.vo.user.UserVo
import com.hugg.domain.repository.AuthRepository
import com.hugg.domain.repository.ForeggJwtRepository
import com.hugg.domain.repository.ProfileRepository
import com.hugg.feature.base.BaseViewModel
import com.hugg.feature.theme.ONBOARDING_CONTENT_1
import com.hugg.feature.theme.ONBOARDING_CONTENT_2
import com.hugg.feature.theme.ONBOARDING_CONTENT_3
import com.hugg.feature.theme.ONBOARDING_CONTENT_4
import com.hugg.feature.theme.ONBOARDING_TITLE_1
import com.hugg.feature.theme.ONBOARDING_TITLE_2
import com.hugg.feature.theme.ONBOARDING_TITLE_3
import com.hugg.feature.theme.ONBOARDING_TITLE_4
import com.hugg.feature.util.ForeggLog
import com.hugg.feature.util.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val foreggJwtRepository: ForeggJwtRepository,
    private val profileRepository: ProfileRepository
) : BaseViewModel<OnboardingPageState>(
    OnboardingPageState()
) {

    private lateinit var accessToken : String

    init {
        initData()
    }

    private fun initData(){
        updateState(
            uiState.value.copy(onboardingList = getOnboardingList())
        )
    }

    private fun getOnboardingList() : List<OnboardingTutorialVo> {
        return listOf(
            OnboardingTutorialVo(
                title = ONBOARDING_TITLE_1,
                content = ONBOARDING_CONTENT_1,
                img = com.hugg.feature.R.drawable.onboarding_first
            ),
            OnboardingTutorialVo(
                title = ONBOARDING_TITLE_2,
                content = ONBOARDING_CONTENT_2,
                img = com.hugg.feature.R.drawable.onboarding_second
            ),
            OnboardingTutorialVo(
                title = ONBOARDING_TITLE_3,
                content = ONBOARDING_CONTENT_3,
                img = com.hugg.feature.R.drawable.onboarding_third
            ),
            OnboardingTutorialVo(
                title = ONBOARDING_TITLE_4,
                content = ONBOARDING_CONTENT_4,
                img = com.hugg.feature.R.drawable.onboarding_fourth
            )
        )
    }

    fun onClickMoveNextPage(nowPage : Int){
        if(nowPage == 3) return
        emitEventFlow(OnboardingEvent.MoveNextPage)
    }

    fun onClickMovePrevPage(){
        emitEventFlow(OnboardingEvent.MovePrevPage)
    }

    fun onClickLastPage(){
        emitEventFlow(OnboardingEvent.MoveLastPage)
    }

    fun login(token : String){
        accessToken = token
        viewModelScope.launch {
            authRepository.login(token).collect {
                resultResponse(it, ::handleLoginSuccess, ::handleLoginError)
            }
        }
    }

    private fun handleLoginSuccess(result : SignResponseVo){
        val request = SaveForeggJwtRequestVo(accessToken = result.accessToken, refreshToken = result.refreshToken)
        viewModelScope.launch {
            foreggJwtRepository.saveAccessTokenAndRefreshToken(request).collect {
                if(it) handleSaveTokenSuccess()
            }
        }
    }

    private fun handleSaveTokenSuccess(){
        updateFcmToken()
        getMyInfo()
    }

    private fun updateFcmToken(){
        FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->
            val request = RenewalFcmRequestVo(token)
            viewModelScope.launch {
                authRepository.renewalFcm(request).collect()
            }
        }
    }

    private fun getMyInfo(){
        viewModelScope.launch {
            profileRepository.getMyInfo().collect{
                resultResponse(it, ::handleSuccessGetMyInfo)
            }
        }
    }

    private fun handleSuccessGetMyInfo(result : ProfileDetailResponseVo){
        val vo = UserVo(name = result.nickName, ssn = result.ssn, genderType = result.genderType, spouse = result.spouse)
        UserInfo.updateInfo(vo)
        goToMain()
    }

    private fun handleLoginError(error : String){
        when(error){
            StatusCode.AUTH.USER_NEED_JOIN -> goToSignUp()
        }
    }

    private fun goToMain(){
        emitEventFlow(OnboardingEvent.GoToMainEvent)
    }

    private fun goToSignUp(){
        emitEventFlow(OnboardingEvent.GoToSignUpEvent(accessToken))
    }
}