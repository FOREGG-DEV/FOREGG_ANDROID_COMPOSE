package com.example.dailyhugg.create

import android.net.Uri
import com.hugg.domain.model.enums.DailyConditionType
import com.hugg.feature.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateDailyHuggViewModel @Inject constructor(

) : BaseViewModel<CreateDailyHuggPageState>(CreateDailyHuggPageState()) {
    fun onDailyHuggContentChange(newContent: String) {
        updateState(
            uiState.value.copy(
                dailyHuggContent = newContent
            )
        )
    }

    fun onSelectedDailyCondition(conditionType: DailyConditionType) {
        updateState(
            uiState.value.copy(
                dailyConditionType = conditionType
            )
        )
    }

    fun setSelectedImageUri(uri: Uri?) {
        updateState(
            uiState.value.copy(
                selectedImageUri = uri
            )
        )
    }
}