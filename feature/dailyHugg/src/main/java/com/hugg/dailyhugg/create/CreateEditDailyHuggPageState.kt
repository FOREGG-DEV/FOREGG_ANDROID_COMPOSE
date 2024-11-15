package com.hugg.dailyhugg.create

import android.net.Uri
import android.os.Parcelable
import com.hugg.domain.model.enums.DailyConditionType
import com.hugg.feature.base.PageState
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class CreateEditDailyHuggPageState(
    val dailyHuggContent: String = "",
    val dailyConditionType: DailyConditionType = DailyConditionType.DEFAULT,
    val selectedImageUri: Uri? = null,
    val specialQuestion : String = "",
): PageState, Parcelable {
    @IgnoredOnParcel
    val clickable: Boolean =
        !(dailyHuggContent.isEmpty() ||
                dailyConditionType == DailyConditionType.DEFAULT)
}