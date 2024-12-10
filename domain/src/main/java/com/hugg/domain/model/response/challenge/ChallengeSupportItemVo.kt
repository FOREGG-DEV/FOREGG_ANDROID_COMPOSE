package com.hugg.domain.model.response.challenge

data class ChallengeSupportItemVo(
    val userId: Long = -1,
    val nickname: String = "",
    val thought: String = "",
    val supported: Boolean = false
)
