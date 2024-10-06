package com.hugg.mypage.cs

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
class MyPageCsViewModel @Inject constructor(
) : BaseViewModel<MyPageCsPageState>(
    MyPageCsPageState()
) {
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