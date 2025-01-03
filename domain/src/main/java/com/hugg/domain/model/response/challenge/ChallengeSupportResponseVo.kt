package com.hugg.domain.model.response.challenge

data class ChallengeSupportResponseVo(
    val item : List<ChallengeSupportItemVo> = emptyList(),
    val currentPage : Int = -1,
    val totalPage : Int = -1
)
