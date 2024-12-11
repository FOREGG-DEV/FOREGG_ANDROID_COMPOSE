package com.hugg.notification

import androidx.lifecycle.viewModelScope
import com.hugg.domain.base.StatusCode
import com.hugg.domain.model.enums.RecordType
import com.hugg.domain.model.request.dailyRecord.InjectionAlarmRequestVo
import com.hugg.domain.model.response.daily.InjectionInfoResponseVo
import com.hugg.domain.repository.DailyRecordRepository
import com.hugg.domain.repository.ProfileRepository
import com.hugg.feature.base.BaseViewModel
import com.hugg.feature.util.TimeFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InjMedDetailViewModel @Inject constructor(
    private val dailyRecordRepository: DailyRecordRepository
) : BaseViewModel<InjMedDetailPageState>(
    InjMedDetailPageState()
) {
    fun initView(time : String, id : Long, type : RecordType){
        updateState(
            uiState.value.copy(pageType = type)
        )
        getInjectionDetail(time, id)
    }

    private fun getInjectionDetail(time: String, id: Long){
        val request = InjectionAlarmRequestVo(
            id = id,
            time = time
        )
        viewModelScope.launch {
            dailyRecordRepository.getInjectionInfo(request).collect {
                resultResponse(it, ::handleSuccessGetInjectionInfo)
            }
        }
    }

    private fun handleSuccessGetInjectionInfo(result : InjectionInfoResponseVo){
        updateState(
            uiState.value.copy(
                date = TimeFormatter.getDotsDate("2024-12-05"),
                time = result.time,
                image = result.image,
                explain = result.description
            )
        )
    }

    fun onClickShare(id: Long, time: String){
        val request = InjectionAlarmRequestVo(id = id, time = time)
        viewModelScope.launch {
            dailyRecordRepository.postShareInjection(request).collect{
                resultResponse(it, { handleShareSuccess() }, ::handleShareError)
            }
        }
    }

    private fun handleShareSuccess(){
        emitEventFlow(InjMedDetailEvent.SuccessShareToast)
    }

    private fun handleShareError(error : String){
        when(error){
            StatusCode.DAILY.SPOUSE_NOT_FOUND -> emitEventFlow(InjMedDetailEvent.ErrorShareToast)
        }
    }
}