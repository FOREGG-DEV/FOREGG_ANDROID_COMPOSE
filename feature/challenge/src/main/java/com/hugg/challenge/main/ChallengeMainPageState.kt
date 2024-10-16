package com.hugg.challenge.main

import com.hugg.domain.model.enums.ChallengeTabType
import com.hugg.domain.model.response.challenge.ChallengeCardVo
import com.hugg.feature.base.PageState

data class ChallengeMainPageState(
    val challengePoint: Int = 0,
    val currentTabType: ChallengeTabType = ChallengeTabType.COMMON,
    val commonChallengeList: List<ChallengeCardVo> = emptyList()
): PageState
