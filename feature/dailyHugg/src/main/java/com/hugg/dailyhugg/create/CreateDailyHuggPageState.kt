package com.hugg.dailyhugg.create

import android.net.Uri
import com.hugg.domain.model.enums.DailyConditionType
import com.hugg.feature.base.PageState

data class CreateDailyHuggPageState(
    val dailyHuggContent: String = "",
    val dailyConditionType: DailyConditionType = DailyConditionType.DEFAULT,
    val selectedImageUri: Uri? = null
): PageState {
    val clickable: Boolean =
        if (
            dailyHuggContent.isEmpty() ||
            dailyConditionType == DailyConditionType.DEFAULT ||
            selectedImageUri == null
        ) false
        else true
}