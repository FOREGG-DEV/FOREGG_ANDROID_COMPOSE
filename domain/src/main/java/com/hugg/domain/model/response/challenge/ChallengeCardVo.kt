package com.hugg.domain.model.response.challenge


data class ChallengeCardVo(
    val id: Long = -1,
    val name: String = "",
    val description: String = "",
    val participants: Int = 0,
    val image: String = "",
    val participating: Boolean = false,
    val open: Boolean = false,
    val point: Int = 0,
    val isCreateChallenge: Boolean = false
)
