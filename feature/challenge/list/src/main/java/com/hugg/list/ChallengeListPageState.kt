package com.hugg.list

import com.hugg.domain.model.response.challenge.AllChallengeItemVo
import com.hugg.feature.base.PageState

data class ChallengeListPageState(
    val challengeList: List<AllChallengeItemVo> = emptyList(),
    val searchKeyword: String = ""
): PageState
