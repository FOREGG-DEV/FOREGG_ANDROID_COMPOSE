package com.hugg.sign

import com.hugg.domain.model.vo.onboarding.OnboardingTutorialVo
import com.hugg.feature.base.PageState

data class OnboardingPageState(
    val onboardingList : List<OnboardingTutorialVo> = emptyList()
) : PageState