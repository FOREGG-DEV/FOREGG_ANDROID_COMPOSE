package com.hugg.challenge.main

import com.hugg.domain.model.enums.ChallengeTabType
import com.hugg.domain.model.response.challenge.ChallengeCardVo
import com.hugg.feature.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChallengeMainViewModel @Inject constructor(

) : BaseViewModel<ChallengeMainPageState>(ChallengeMainPageState()) {

    private val dummyData = listOf(
        ChallengeCardVo(id = 1, name = "시술 직전부터는 금주", description = "시술 직전~도중에는 완전히 피하는 게 좋아요.\n" +
                "전문가들은 시술 직전~도중엔  소주 50cc, 또는 맥주 200cc\n" +
                "이하의 알코올 섭취도 피하라고 말합니다.", participants = 19250, ifMine = false),
        ChallengeCardVo(id = 2, name = "시술 직전부터는 금주", description = "시술 직전~도중에는 완전히 피하는 게 좋아요.\n" +
                "전문가들은 시술 직전~도중엔  소주 50cc, 또는 맥주 200cc\n" +
                "이하의 알코올 섭취도 피하라고 말합니다.", participants = 19250, ifMine = false),
        ChallengeCardVo(id = 3, name = "시술 직전부터는 금주", description = "시술 직전~도중에는 완전히 피하는 게 좋아요.\n" +
                "전문가들은 시술 직전~도중엔  소주 50cc, 또는 맥주 200cc\n" +
                "이하의 알코올 섭취도 피하라고 말합니다.", participants = 19250, ifMine = false),
        ChallengeCardVo(id = 4, name = "시술 직전부터는 금주", description = "시술 직전~도중에는 완전히 피하는 게 좋아요.\n" +
                "전문가들은 시술 직전~도중엔  소주 50cc, 또는 맥주 200cc\n" +
                "이하의 알코올 섭취도 피하라고 말합니다.", participants = 19250, ifMine = false),
        ChallengeCardVo(id = 5, name = "시술 직전부터는 금주", description = "시술 직전~도중에는 완전히 피하는 게 좋아요.\n" +
                "전문가들은 시술 직전~도중엔  소주 50cc, 또는 맥주 200cc\n" +
                "이하의 알코올 섭취도 피하라고 말합니다.", participants = 19250, ifMine = false),
        ChallengeCardVo(id = 6, name = "시술 직전부터는 금주", description = "시술 직전~도중에는 완전히 피하는 게 좋아요.\n" +
                "전문가들은 시술 직전~도중엔  소주 50cc, 또는 맥주 200cc\n" +
                "이하의 알코올 섭취도 피하라고 말합니다.", participants = 19250, ifMine = false)
    )

    init {
        updateState(
            uiState.value.copy(
                commonChallengeList = dummyData
            )
        )
    }

    fun updateTabType(type: ChallengeTabType) {
        updateState(
            uiState.value.copy(
                currentTabType = type
            )
        )
    }

    fun createNickname(nickname: String) {
        updateShowDialog(true)
    }

    fun updateShowDialog(value: Boolean) {
        updateState(
            uiState.value.copy(
                showChallengeCompleteDialog = value
            )
        )
    }
}