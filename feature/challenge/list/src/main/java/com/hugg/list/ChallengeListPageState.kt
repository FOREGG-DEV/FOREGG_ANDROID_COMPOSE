package com.hugg.list

import com.hugg.domain.model.response.challenge.ChallengeCardVo
import com.hugg.feature.base.PageState

data class ChallengeListPageState(
    val challengeList: List<ChallengeCardVo> = emptyList(),
    val searchKeyword: String = "",
    val selectedChallenge: ChallengeCardVo? = null,
    val emptyKeyword: String = ""
): PageState
