package com.hugg.challenge.main

import com.hugg.domain.model.enums.ChallengeTabType
import com.hugg.domain.model.enums.MyChallengeState
import com.hugg.domain.model.response.challenge.ChallengeCardVo
import com.hugg.domain.model.response.challenge.MyChallengeListItemVo
import com.hugg.feature.base.PageState

data class ChallengeMainPageState(
    val challengePoint: Int = 0,
    val currentTabType: ChallengeTabType = ChallengeTabType.COMMON,
    val commonChallengeList: List<ChallengeCardVo> = emptyList(),
    val firstDateOfWeek: String = "",
    val myChallengeList: List<MyChallengeListItemVo> = emptyList(),
    val currentChallengeState: List<MyChallengeState> = emptyList(),
    val showChallengeCompleteDialog: Boolean = false,
    val currentChallengeDayOfWeek: Int = 0,
    val successCount: Int = 0,
    val showCommentDialog: Boolean = false,
    val showDeleteDialog: Boolean = false,
    val showChallengeSuccessAnimation: Boolean = false
): PageState
