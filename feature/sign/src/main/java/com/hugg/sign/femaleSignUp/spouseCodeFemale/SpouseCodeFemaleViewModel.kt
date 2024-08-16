package com.hugg.sign.femaleSignUp.spouseCodeFemale

import androidx.lifecycle.viewModelScope
import com.google.firebase.messaging.FirebaseMessaging
import com.hugg.domain.model.request.sign.SaveForeggJwtRequestVo
import com.hugg.domain.model.request.sign.SignUpRequestVo
import com.hugg.domain.model.request.sign.SignUpWithTokenRequestVo
import com.hugg.domain.model.response.profile.ProfileDetailResponseVo
import com.hugg.domain.model.response.sign.ShareCodeResponseVo
import com.hugg.domain.model.response.sign.SignResponseVo
import com.hugg.domain.model.vo.user.UserVo
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
class SpouseCodeFemaleViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val profileRepository: ProfileRepository,
    private val foreggJwtRepository: ForeggJwtRepository
) : BaseViewModel<SpouseCodeFemalePageState>(
    SpouseCodeFemalePageState()
) {

    init {
        getSpouseCode()
    }

    private fun getSpouseCode(){
        viewModelScope.launch {
            authRepository.getShareCode().collect {
                resultResponse(it, ::handleSuccessGetSpouseCode)
            }
        }
    }

    private fun handleSuccessGetSpouseCode(result : ShareCodeResponseVo){
        updateState(uiState.value.copy(
            spouseCode = result.shareCode
        ))
    }

    fun onClickSignUp(accessToken : String, vo : SignUpRequestVo){
        FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->
            val request = getRequest(token, accessToken, vo)
            viewModelScope.launch {
                authRepository.join(request).collect{
                    resultResponse(it, ::handleLoginSuccess)
                }
            }
        }
    }

    private fun getRequest(fcmToken : String, accessToken: String, vo: SignUpRequestVo) : SignUpWithTokenRequestVo {
        return SignUpWithTokenRequestVo(
            accessToken = accessToken,
            signUpRequestVo = vo.copy(
                spouseCode = uiState.value.spouseCode,
                fcmToken = fcmToken
            )
        )
    }

    private fun handleLoginSuccess(result : SignResponseVo){
        val request = SaveForeggJwtRequestVo(accessToken = result.accessToken, refreshToken = result.refreshToken)
        viewModelScope.launch {
            foreggJwtRepository.saveAccessTokenAndRefreshToken(request).collect{
                if(it) getMyInfo()
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

    private fun goToMain(){
        emitEventFlow(SpouseCodeFemaleEvent.GoToMainEvent)
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