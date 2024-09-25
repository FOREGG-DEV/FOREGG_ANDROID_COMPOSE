package com.hugg.dailyhugg.main

import androidx.lifecycle.viewModelScope
import com.hugg.domain.model.response.dailyHugg.DailyHuggItemVo
import com.hugg.domain.repository.DailyHuggRepository
import com.hugg.feature.base.BaseViewModel
import com.hugg.feature.util.TimeFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyHuggViewModel @Inject constructor(
    private val dailyHuggRepository: DailyHuggRepository
): BaseViewModel<DailyHuggPageState>(
    DailyHuggPageState()
) {
    fun getDailyHuggByDate(date: String) {
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
            getDailyHuggByDate(newDate)
            updateState(
                uiState.value.copy(
                    date = newDate,
                    day = newDay
                )
            )
        }
    }

    fun updateShowEditDialog(value: Boolean) {
        updateState(
            uiState.value.copy(
                showEditDialog = value
            )
        )
    }

    fun updateShowDeleteDialog(value: Boolean) {
        updateState(
            uiState.value.copy(
                showDeleteDialog = value
            )
        )
    }

    fun deleteDailyHugg() {
        viewModelScope.launch {
            dailyHuggRepository.deleteDailyHugg(uiState.value.dailyHugg!!.id).collect {
                resultResponse(it, { onSuccessDeleteDailyHugg() })
            }
        }
    }

    private fun onSuccessDeleteDailyHugg() {
        getDailyHuggByDate(TimeFormatter.getToday())
        emitEventFlow(DailyHuggEvent.CompleteDeleteDailyHugg)
    }
}