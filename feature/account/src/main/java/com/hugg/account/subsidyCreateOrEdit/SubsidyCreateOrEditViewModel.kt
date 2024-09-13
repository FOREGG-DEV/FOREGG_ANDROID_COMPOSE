package com.hugg.account.subsidyCreateOrEdit

import androidx.lifecycle.viewModelScope
import com.hugg.account.subsidyList.SubsidyListPageState
import com.hugg.domain.model.enums.CreateOrEditType
import com.hugg.domain.model.request.account.SubsidyCreateEditRequestVo
import com.hugg.domain.model.request.account.SubsidyRequestVo
import com.hugg.domain.model.response.account.SubsidyDetailResponseVo
import com.hugg.domain.model.response.account.SubsidyListResponseVo
import com.hugg.domain.repository.AccountRepository
import com.hugg.feature.base.BaseViewModel
import com.hugg.feature.util.ForeggLog
import com.hugg.feature.util.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import javax.inject.Inject

@HiltViewModel
class SubsidyCreateOrEditViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : BaseViewModel<SubsidyCreateOrEditPageState>(
    SubsidyCreateOrEditPageState()
) {

    fun initTypeAndId(type : CreateOrEditType, id : Long, round : Int){
        updateState(
            uiState.value.copy(
                pageType = type,
                id = id,
                nowRound = round
            )
        )

        if(type == CreateOrEditType.EDIT) getSubsidyDetail()
    }

    fun showDeleteDialog(){
        updateState(
            uiState.value.copy(isShowDialog = true)
        )
    }

    fun cancelDeleteDialog(){
        updateState(
            uiState.value.copy(isShowDialog = false)
        )
    }

    fun deleteSubsidy(){
        viewModelScope.launch {
            accountRepository.deleteSubsidy(uiState.value.id).collect {
                resultResponse(it, { emitEventFlow(SubsidyCreateOrEditEvent.SuccessDeleteSubsidyEvent) })
            }
        }
    }

    fun onChangedNickname(nickname : String){
        if(nickname.length > 2) return
        updateState(
            uiState.value.copy(nickname = nickname)
        )
    }

    fun onChangedContent(content : String){
        if(content.length > 250) return
        updateState(
            uiState.value.copy(content = content)
        )
    }

    fun onChangedMoney(money : String){
        val newMoney = try {
            val parsedValue = money.replace(",", "").toLong()
            DecimalFormat("#,###").format(parsedValue)
        } catch (e: NumberFormatException) {
            money
        }

        if(money == uiState.value.money) return
        updateState(
            uiState.value.copy(money =  newMoney)
        )
    }

    fun createOrEdit(){
        when(uiState.value.pageType){
            CreateOrEditType.CREATE -> createSubsidy()
            CreateOrEditType.EDIT -> modifySubsidy()
        }
    }

    private fun createSubsidy(){
        val request = getCreateEditRequest()
        viewModelScope.launch {
            accountRepository.createSubsidy(request).collect {
                resultResponse(it, { emitEventFlow(SubsidyCreateOrEditEvent.SuccessCreateSubsidyEvent) })
            }
        }
    }

    private fun modifySubsidy(){
        val request = getSubsidyRequest()
        viewModelScope.launch {
            accountRepository.modifySubsidy(request).collect {
                resultResponse(it, { emitEventFlow(SubsidyCreateOrEditEvent.SuccessModifySubsidyEvent) })
            }
        }
    }

    private fun getSubsidyRequest() : SubsidyRequestVo {
        return SubsidyRequestVo(
            id = uiState.value.id,
            request = getCreateEditRequest()
        )
    }

    private fun getCreateEditRequest() : SubsidyCreateEditRequestVo {
        return SubsidyCreateEditRequestVo(
            nickname = uiState.value.nickname,
            content = uiState.value.content,
            amount = uiState.value.money.replace(",", "").toInt(),
            count = uiState.value.nowRound
        )
    }

    private fun getSubsidyDetail() {
        viewModelScope.launch {
            accountRepository.getSubsidyDetail(uiState.value.id).collect {
                resultResponse(it, ::handleSuccessGetSubsidyDetail)
            }
        }
    }

    private fun handleSuccessGetSubsidyDetail(result : SubsidyDetailResponseVo){
        updateState(
            uiState.value.copy(
                nickname = result.nickname,
                content = result.content,
            )
        )
        onChangedMoney(result.amount.toString())
    }
}