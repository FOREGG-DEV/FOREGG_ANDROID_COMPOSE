package com.hugg.sign.maleSignUp

import androidx.lifecycle.viewModelScope
import com.google.firebase.messaging.FirebaseMessaging
import com.hugg.domain.base.StatusCode
import com.hugg.domain.model.request.sign.SaveForeggJwtRequestVo
import com.hugg.domain.model.request.sign.SignUpMaleRequestVo
import com.hugg.domain.model.request.sign.SignUpWithTokenMaleRequestVo
import com.hugg.domain.model.response.profile.ProfileDetailResponseVo
import com.hugg.domain.model.response.sign.SignResponseVo
import com.hugg.domain.repository.AuthRepository
import com.hugg.domain.repository.ForeggJwtRepository
import com.hugg.domain.repository.ProfileRepository
import com.hugg.feature.base.BaseViewModel
import com.hugg.feature.util.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MaleSignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val foreggJwtRepository: ForeggJwtRepository,
    private val profileRepository: ProfileRepository
) : BaseViewModel<MaleSignUpPageState>(
    MaleSignUpPageState()
) {

    fun onClickSignUp(accessToken: String, vo : SignUpMaleRequestVo){
        FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->
            val request = getRequest(token, accessToken, vo)
            viewModelScope.launch {
                authRepository.joinMale(request).collect{
                    resultResponse(it, ::handleJoinSuccess, ::handleJoinError)
                }
            }
        }
    }

    private fun handleJoinSuccess(result : SignResponseVo){
        val request = SaveForeggJwtRequestVo(accessToken = result.accessToken, refreshToken = result.refreshToken)
        viewModelScope.launch {
            foreggJwtRepository.saveAccessTokenAndRefreshToken(request).collect{
                if(it) getMyInfo()
            }
        }
    }

    private fun handleJoinError(error : String){
        when(error){
            StatusCode.AUTH.NOT_CORRECT_SHARE_CODE -> emitEventFlow(MaleSignUpEvent.ShowErrorSpouseCodeEvent)
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
        UserInfo.updateInfo(result)
        goToMain()
    }

    private fun goToMain(){
        emitEventFlow(MaleSignUpEvent.GoToMainEvent)
    }
    private fun getRequest(fcmToken : String, accessToken : String, vo : SignUpMaleRequestVo) : SignUpWithTokenMaleRequestVo {
        return SignUpWithTokenMaleRequestVo(
            accessToken = accessToken,
            signUpMaleRequestVo = vo.copy(spouseCode = uiState.value.spouseCode, fcmToken = fcmToken)
        )
    }

    fun onChangedSpouseCode(text : String){
        updateState(uiState.value.copy(
            spouseCode = text
        ))
    }
}