package com.hugg.domain.model.response.challenge

data class MyChallengeListItemVo (
    val description: String = "",
    val id: Long = 0,
    val image: String = "",
    val name: String = "",
    val participants: Int = 0,
    val successDays: List<String>? = emptyList(),
    val weekOfMonth: String = "",
    val lastSaturday : Boolean = false,
    val isCompleteToday : Boolean = false,
)