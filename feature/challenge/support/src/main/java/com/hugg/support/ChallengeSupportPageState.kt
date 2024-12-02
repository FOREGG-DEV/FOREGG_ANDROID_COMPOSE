package com.hugg.support

import com.hugg.domain.model.response.challenge.ChallengeSupportItemVo
import com.hugg.feature.base.PageState

data class ChallengeSupportPageState(
    val completedList: List<ChallengeSupportItemVo> = emptyList(),
    val incompleteList: List<ChallengeSupportItemVo> = emptyList()
): PageState
