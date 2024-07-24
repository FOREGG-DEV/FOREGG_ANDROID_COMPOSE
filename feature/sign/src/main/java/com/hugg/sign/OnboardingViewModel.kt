package com.hugg.sign

import androidx.lifecycle.viewModelScope
import com.hugg.domain.model.vo.onboarding.OnboardingTutorialVo
import com.hugg.feature.base.BaseViewModel
import com.hugg.feature.theme.ONBOARDING_CONTENT_1
import com.hugg.feature.theme.ONBOARDING_CONTENT_2
import com.hugg.feature.theme.ONBOARDING_CONTENT_3
import com.hugg.feature.theme.ONBOARDING_CONTENT_4
import com.hugg.feature.theme.ONBOARDING_TITLE_1
import com.hugg.feature.theme.ONBOARDING_TITLE_2
import com.hugg.feature.theme.ONBOARDING_TITLE_3
import com.hugg.feature.theme.ONBOARDING_TITLE_4
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor() : BaseViewModel<OnboardingPageState>(
    OnboardingPageState()
) {

    init {
        initData()
    }

    private fun initData(){
        viewModelScope.launch {
            _uiState.update { it.copy(
                onboardingList = getOnboardingList()
            ) }
        }
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
}