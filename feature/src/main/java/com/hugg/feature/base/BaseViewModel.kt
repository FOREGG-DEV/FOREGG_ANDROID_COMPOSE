package com.hugg.feature.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hugg.data.base.StatusCode
import com.hugg.domain.base.ApiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<STATE: PageState>(
    initialPageState : STATE
) : ViewModel() {

    protected val _uiState = MutableStateFlow(initialPageState)
    val uiState = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<Event>(replay = 1)
    val eventFlow = _eventFlow.asSharedFlow()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _commonError = MutableLiveData<String>()
    val commonError: LiveData<String> = _commonError

    protected fun updateState(state: STATE) {
        viewModelScope.launch {
            _uiState.update { state }
        }
    }
    protected fun emitEventFlow(event: Event) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    private fun showLoading(){
        _isLoading.value = true
    }

    private fun endLoading(){
        _isLoading.value = false
    }

    protected fun<D> resultResponse(response: ApiState<D>, successCallback : (D) -> Unit, errorCallback : ((String) -> Unit)? = null, needLoading : Boolean = false){
        when(response){
            is ApiState.Error -> {
                if(response.errorCode == StatusCode.ERROR_404 ||
                    response.errorCode == StatusCode.ERROR ||
                    response.errorCode == StatusCode.NETWORK_ERROR) {
                    //ForeggAnalytics.logEvent("error_${response.errorCode}_${response.data.toString()}", "apiScreen")
                    _commonError.value = response.errorCode
                }
                else errorCallback?.invoke(response.errorCode)
                endLoading()
            }
            ApiState.Loading -> if(needLoading) showLoading()
            is ApiState.Success -> {
                successCallback.invoke(response.data)
                endLoading()
            }
        }
    }
}