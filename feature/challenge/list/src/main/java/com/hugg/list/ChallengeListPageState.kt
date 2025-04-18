package com.hugg.list

import com.hugg.domain.model.response.challenge.ChallengeCardVo
import com.hugg.feature.base.PageState

data class ChallengeListPageState(
    val challengeList: List<ChallengeCardVo> = emptyList(),
    val searchKeyword: String = "",
    val emptyKeyword: String = "",
    val challengeDetailItem: ChallengeCardVo = ChallengeCardVo()
): PageState
