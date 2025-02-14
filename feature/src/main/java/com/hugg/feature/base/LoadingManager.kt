package com.hugg.feature.base

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object LoadingManager {
    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> = _loadingState.asStateFlow()

    private val _errorState = MutableStateFlow("")
    val errorState: StateFlow<String> = _errorState.asStateFlow()

    suspend fun setLoadingState(isLoading: Boolean) {
        _loadingState.emit(isLoading)
    }

    suspend fun setCommonErrorState(error: String) {
        _errorState.emit(error)
    }
}