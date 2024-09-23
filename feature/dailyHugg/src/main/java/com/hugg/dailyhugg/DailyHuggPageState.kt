package com.hugg.dailyhugg

import com.hugg.domain.model.response.dailyHugg.DailyHuggItemVo
import com.hugg.feature.base.PageState

data class DailyHuggPageState(
    val round: Int = 0, // 회차를 나타내는 변수
    val dailyHugg: List<DailyHuggItemVo> = emptyList()  // 특정 날짜의 데일리 허그를 나타내는 변수
): PageState