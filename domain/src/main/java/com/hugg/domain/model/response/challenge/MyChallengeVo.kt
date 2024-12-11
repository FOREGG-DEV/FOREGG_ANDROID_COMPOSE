package com.hugg.domain.model.response.challenge

data class MyChallengeVo(
    val firstDateOfWeek: String = "",
    val dtos: List<MyChallengeListItemVo> = emptyList()
)
