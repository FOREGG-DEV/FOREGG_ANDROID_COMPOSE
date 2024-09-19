package com.example.dailyhugg.create

import androidx.compose.ui.graphics.Color
import com.hugg.domain.model.enums.DailyConditionType
import com.hugg.feature.base.PageState
import com.hugg.feature.theme.InActiveMainNormal
import com.hugg.feature.theme.MainNormal

data class CreateDailyHuggPageState(
    val dailyHuggContent: String = "",
    val dailyConditionType: DailyConditionType = DailyConditionType.DEFAULT
): PageState {
    val clickable: Boolean = if (dailyHuggContent.isEmpty() || dailyConditionType == DailyConditionType.DEFAULT) false else true
    val btnRegistrationColor: Color
        get() = if (clickable) MainNormal else InActiveMainNormal
}