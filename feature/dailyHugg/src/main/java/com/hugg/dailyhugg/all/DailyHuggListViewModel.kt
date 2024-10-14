package com.hugg.dailyhugg.all

import androidx.lifecycle.viewModelScope
import com.hugg.domain.model.response.dailyHugg.DailyHuggListResponseVo
import com.hugg.domain.repository.DailyHuggRepository
import com.hugg.feature.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyHuggListViewModel @Inject constructor(
    private val dailyHuggRepository: DailyHuggRepository
) : BaseViewModel<DailyHuggListPageState>(DailyHuggListPageState()) {

    fun getDailyHuggList(page: Int) {
        viewModelScope.launch {
            dailyHuggRepository.getDailyHuggList(page = page).collect {
                resultResponse(it, ::onSuccessGetDailyHuggList)
            }
        }
    }

    private fun onSuccessGetDailyHuggList(response: DailyHuggListResponseVo) {
        updateState(
            uiState.value.copy(
                dailyHuggList = uiState.value.dailyHuggList + response.dailyHuggList,
                currentPage = response.currentPage,
                totalPages = response.totalPages,
                totalItems = response.totalItems
            )
        )
    }
}