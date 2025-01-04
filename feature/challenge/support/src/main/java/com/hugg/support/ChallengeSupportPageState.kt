package com.hugg.support

import com.hugg.domain.model.response.challenge.ChallengeSupportResponseVo
import com.hugg.feature.base.PageState

data class ChallengeSupportPageState(
    val completedList: ChallengeSupportResponseVo = ChallengeSupportResponseVo(),
    val incompleteList: ChallengeSupportResponseVo = ChallengeSupportResponseVo(),
    val challengeId: Long = -1
): PageState
