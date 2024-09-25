package com.hugg.dailyhugg.main

import androidx.lifecycle.viewModelScope
import com.hugg.domain.model.response.dailyHugg.DailyHuggItemVo
import com.hugg.domain.repository.DailyHuggRepository
import com.hugg.feature.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyHuggViewModel @Inject constructor(
    private val dailyHuggRepository: DailyHuggRepository
): BaseViewModel<DailyHuggPageState>(
    DailyHuggPageState()
) {
    private fun getDailyHuggBYDate(date: String) {
        viewModelScope.launch {
            dailyHuggRepository.getDailyHuggByDate(date).collect {
                resultResponse(it, ::onSuccessGetDailyHuggByDate, ::onFailedGetDailyHuggByDate, needLoading = true)
            }
        }
    }

    private fun onSuccessGetDailyHuggByDate(dailyHugg: DailyHuggItemVo) {
        updateState(
            uiState.value.copy(
                dailyHugg = dailyHugg
            )
        )
    }

    private fun onFailedGetDailyHuggByDate(code: String) {
        when(code) {
            "DAILY4002" -> {
                updateState(
                    uiState.value.copy(
                        dailyHugg = null
                    )
                )
            }
        }
    }

    fun setDate(newDate: String, newDay: String) {
        viewModelScope.launch {
            getDailyHuggBYDate(newDate)
            updateState(
                uiState.value.copy(
                    date = newDate,
                    day = newDay
                )
            )
        }
    }

    fun updateShowDialog(value: Boolean) {
        updateState(
            uiState.value.copy(
                showEditDialog = value
            )
        )
    }
}