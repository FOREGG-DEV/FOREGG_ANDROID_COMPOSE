package com.hugg.dailyhugg.reply

import androidx.lifecycle.viewModelScope
import com.hugg.domain.model.enums.DailyConditionType
import com.hugg.domain.model.enums.DailyHuggReplyType
import com.hugg.domain.model.request.dailyHugg.ReplyDailyHuggRequestVo
import com.hugg.domain.model.response.dailyHugg.DailyHuggItemVo
import com.hugg.domain.repository.DailyHuggRepository
import com.hugg.feature.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReplyDailyHuggViewModel @Inject constructor(
    private val dailyHuggRepository: DailyHuggRepository
): BaseViewModel<ReplyDailyHuggPageState>(
    ReplyDailyHuggPageState()
) {
    fun getDailyHuggByDate(date: String) {
        viewModelScope.launch {
            dailyHuggRepository.getDailyHuggByDate(date).collect {
                resultResponse(it, ::onSuccessGetDailyHuggByDate, needLoading = true)
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

    fun onSelectedReplyType(replyType: DailyHuggReplyType) {
        updateState(
            uiState.value.copy(
                selectedReplyType = replyType
            )
        )
    }

    fun onChangedReply(reply : String){
        updateState(
            uiState.value.copy(
                reply = reply
            )
        )
    }

    fun onClickReplyRegisterBtn(){
        val request = ReplyDailyHuggRequestVo(
            id = uiState.value.dailyHugg!!.id,
            replyEmojiType = uiState.value.selectedReplyType,
            content = uiState.value.reply
        )
        viewModelScope.launch {
            dailyHuggRepository.replyDailyHugg(request).collect {
                resultResponse(it, { emitEventFlow(ReplyDailyHuggEvent.CompleteReplyDailyHugg) } )
            }
        }
    }
}