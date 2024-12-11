package com.hugg.create

import com.hugg.feature.R
import com.hugg.feature.base.PageState

data class CreateChallengePageState(
    val selectedEmojiIndex: Int = -1,
    val emojiList: List<Pair<Int, String>> = listOf(
        R.drawable.ic_heart_black to "BLACK",
        R.drawable.ic_heart_purple to "PURPLE",
        R.drawable.ic_heart_blue to "BLUE",
        R.drawable.ic_heart_green to "GREEN",
        R.drawable.ic_heart_yellow to "YELLOW",
        R.drawable.ic_heart_orange to "ORANGE",
        R.drawable.ic_heart_red to "RED",
    ),
    val challengeName: String = "",
    val challengeDescription: String = "",
    val showEmojiList: Boolean = false
): PageState
