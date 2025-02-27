package com.hugg.domain.model.response.challenge

data class MyChallengeListItemVo (
    val id: Long = 0,
    val image: String = "",
    val name: String = "",
    val participants: Int = 0,
    val successDays: List<String> = emptyList(),
    val startDate: String = "",
    val firstDate: String = "",
    val isCompleteToday : Boolean = false
)