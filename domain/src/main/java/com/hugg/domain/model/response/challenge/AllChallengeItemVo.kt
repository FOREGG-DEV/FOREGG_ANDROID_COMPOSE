package com.hugg.domain.model.response.challenge

data class AllChallengeItemVo(
    val id: Long = 0,
    val point: Int = 0,
    val image: String = "",
    val name: String = "",
    val description: String = "",
    val participants: Int = 0,
    val open: Boolean = true,
    val participating: Boolean = true
)
