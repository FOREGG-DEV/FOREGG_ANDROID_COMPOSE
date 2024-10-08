package com.hugg.home.homeMain

import com.hugg.domain.model.response.challenge.MyChallengeListItemVo
import com.hugg.domain.model.vo.home.HomeTodayScheduleCardVo
import com.hugg.feature.base.PageState

data class HomePageState(
    val todayScheduleList : List<HomeTodayScheduleCardVo> = emptyList(),
    val challengeList: List<MyChallengeListItemVo> = emptyList(),
) : PageState