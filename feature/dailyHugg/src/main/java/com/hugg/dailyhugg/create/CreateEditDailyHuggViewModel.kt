package com.hugg.dailyhugg.create

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.hugg.domain.model.enums.DailyConditionType
import com.hugg.domain.model.vo.dailyHugg.CreateDailyHuggDto
import com.hugg.domain.repository.DailyHuggRepository
import com.hugg.feature.base.BaseViewModel
import com.hugg.feature.util.ForeggLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class CreateEditDailyHuggViewModel @Inject constructor(
    private val dailyHuggRepository: DailyHuggRepository
) : BaseViewModel<CreateEditDailyHuggPageState>(CreateEditDailyHuggPageState()) {
    private var dailyHuggId: Long? = null
    private val gson = Gson()

    fun setPageState(pageState: CreateEditDailyHuggPageState?, id: Long?) {
        dailyHuggId = id
        pageState?.let {
            updateState(
                state = pageState
            )
        }
    }

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

    fun createDailyHugg(image: MultipartBody.Part?) {
        val dtoJson = gson.toJson(CreateDailyHuggDto(
            dailyConditionType = uiState.value.dailyConditionType,
            content = uiState.value.dailyHuggContent
        ))
        val dtoRequestBody = dtoJson.toRequestBody("application/json".toMediaTypeOrNull())
        viewModelScope.launch {
            dailyHuggRepository.createDailyHugg(
                image = image!!,
                dto = dtoRequestBody
            ).collect {
                resultResponse(it, { handleCreateDailyHuggSuccess() }, ::handleCreateDailyHuggError)
            }
        }
    }

    private fun handleCreateDailyHuggSuccess() {
        emitEventFlow(CreateEditDailyHuggEvent.GoToDailyHuggCreationSuccess)
    }

    private fun handleCreateDailyHuggError(code: String) {
        when(code) {
            "DAILY4001" -> emitEventFlow(CreateEditDailyHuggEvent.AlreadyExistEditDailyHugg)
        }
    }

    fun editDailyHugg(image: MultipartBody.Part?) {
        val dtoJson = gson.toJson(CreateDailyHuggDto(
            dailyConditionType = uiState.value.dailyConditionType,
            content = uiState.value.dailyHuggContent
        ))
        val dtoRequestBody = dtoJson.toRequestBody("application/json".toMediaTypeOrNull())
        viewModelScope.launch {
            dailyHuggRepository.editDailyHugg(id = dailyHuggId!!, image = image!!, dto = dtoRequestBody).collect {
                resultResponse(it, { emitEventFlow(CreateEditDailyHuggEvent.CompleteEditDailyHugg) })
            }
        }
    }
}