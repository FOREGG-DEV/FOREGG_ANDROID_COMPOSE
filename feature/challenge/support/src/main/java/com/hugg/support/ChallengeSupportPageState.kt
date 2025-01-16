package com.hugg.support

import com.hugg.domain.model.response.challenge.ChallengeSupportItemVo
import com.hugg.feature.base.PageState

data class ChallengeSupportPageState(
    val completedList: List<ChallengeSupportItemVo> = emptyList(),
    val incompleteList: List<ChallengeSupportItemVo> = emptyList(),
    val challengeId: Long = -1,
    val completedCurPage: Int = 0,
    val completedTotalPage: Int = 0,
    val incompleteCurPage: Int = 0,
    val incompleteTotalPage: Int = 0
): PageState
