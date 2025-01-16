package com.hugg.domain.model.response.challenge

data class ChallengeSupportListVo(
    val items: List<ChallengeSupportItemVo> = emptyList(),
    val currentPage: Int = 0,
    val totalPage: Int = 0,
    val totalItems: Int = 0
)
