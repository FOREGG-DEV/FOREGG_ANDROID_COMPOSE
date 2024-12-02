package com.hugg.domain.model.response.challenge

data class ChallengeSupportItemVo(
    val id: Long = -1,
    val challengeNickname: String = "",
    val thought: String = "",
    val success: Boolean = false,
    val clap: Boolean = false,
    val support: Boolean = false
)
