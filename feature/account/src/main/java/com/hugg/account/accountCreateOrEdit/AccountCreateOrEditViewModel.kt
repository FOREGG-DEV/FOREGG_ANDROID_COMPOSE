package com.hugg.account.accountCreateOrEdit

import androidx.lifecycle.viewModelScope
import com.hugg.domain.model.enums.AccountColorType
import com.hugg.domain.model.enums.CreateOrEditType
import com.hugg.domain.model.request.account.AccountCreateRequestVo
import com.hugg.domain.model.request.account.AccountEditRequestVo
import com.hugg.domain.model.request.account.ExpenditureRequestItem
import com.hugg.domain.model.response.account.AccountDetailResponseVo
import com.hugg.domain.model.response.account.SubsidyListResponseVo
import com.hugg.domain.model.vo.account.AccountExpenditureItemVo
import com.hugg.domain.repository.AccountRepository
import com.hugg.feature.base.BaseViewModel
import com.hugg.feature.theme.ACCOUNT_PERSONAL
import com.hugg.feature.util.ForeggLog
import com.hugg.feature.util.TimeFormatter
import com.hugg.feature.util.UnitFormatter
import com.hugg.feature.util.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import javax.inject.Inject

@HiltViewModel
class AccountCreateOrEditViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : BaseViewModel<AccountCreateOrEditPageState>(
    AccountCreateOrEditPageState()
) {

    companion object{
        const val MAX_CONTENT_LENGTH = 24
    }

    fun initView(id : Long, type : CreateOrEditType) {
        updateState(
            uiState.value.copy(
                id = id,
                pageType = type
            )
        )
        when(type){
            CreateOrEditType.CREATE -> getSubsidyByCount(uiState.value.nowRound)
            CreateOrEditType.EDIT -> getAccountDetail(id)
        }
    }

    fun selectStartDate(date : String){
        updateState(uiState.value.copy(
            date = if(TimeFormatter.isAfter(date, TimeFormatter.getToday())) TimeFormatter.getToday() else date
        ))
    }

    fun onClickPlusBtn(){
        if(uiState.value.nowRound == UserInfo.info.round) return
        val newRound = uiState.value.nowRound + 1
        updateState(uiState.value.copy(
            nowRound = newRound
        ))
        getSubsidyByCount(newRound)
    }

    fun onClickMinusBtn(){
        if(uiState.value.nowRound == 0) return
        val newRound = uiState.value.nowRound - 1
        updateState(uiState.value.copy(
            nowRound = newRound
        ))
        getSubsidyByCount(newRound)
    }

    fun onChangedContent(content : String){
        if(content.length == MAX_CONTENT_LENGTH) return
        updateState(
            uiState.value.copy(content = content)
        )
    }

    fun onChangedMemo(memo : String){
        updateState(
            uiState.value.copy(memo = memo)
        )
    }

    fun onChangedMoney(money : String, name : String){
        val newList = uiState.value.expenditureList.map {
            it.copy(
                money = if (it.nickname == name) UnitFormatter.getMoneyFormat(money) else it.money
            )
        }
        updateState(
            uiState.value.copy(
                expenditureList = newList
            )
        )
    }

    fun onClickCreateOrEdit(){
        when(uiState.value.pageType){
            CreateOrEditType.CREATE -> createAccount()
            CreateOrEditType.EDIT -> modifyAccount()
        }
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

    fun deleteAccount(){
        viewModelScope.launch {
            accountRepository.delete(uiState.value.id).collect {
                resultResponse(it, { emitEventFlow(AccountCreateOrEditEvent.SuccessDeleteAccountEvent) } )
            }
        }
    }

    private fun getSubsidyByCount(count : Int){
        viewModelScope.launch {
            accountRepository.getSubsidies(count).collect {
                resultResponse(it, ::handleSuccessGetSubsidies)
            }
        }
    }

    private fun handleSuccessGetSubsidies(result : List<SubsidyListResponseVo>){
        val expenditureList = listOf(
            AccountExpenditureItemVo(
                color = AccountColorType.RED,
                nickname = ACCOUNT_PERSONAL,
            )
        ) + result.map {
            AccountExpenditureItemVo(
                color = it.color,
                nickname = it.nickname,
            )
        }

        updateState(
            uiState.value.copy(expenditureList = expenditureList)
        )

        if(uiState.value.pageType == CreateOrEditType.EDIT) setOriginExpenditure()
    }

    private fun createAccount(){
        val request = getAccountCreateRequest()
        viewModelScope.launch {
            accountRepository.createAccount(request).collect {
                resultResponse(it, { emitEventFlow(AccountCreateOrEditEvent.SuccessCreateAccountEvent) } )
            }
        }
    }

    private fun modifyAccount(){
        val request = AccountEditRequestVo(
            id = uiState.value.id,
            request = getAccountCreateRequest()
        )
        viewModelScope.launch {
            accountRepository.editAccount(request).collect {
                resultResponse(it, { emitEventFlow(AccountCreateOrEditEvent.SuccessModifyAccountEvent) } )
            }
        }
    }

    private fun getAccountCreateRequest() : AccountCreateRequestVo {
        return AccountCreateRequestVo(
            date = uiState.value.date,
            count = uiState.value.nowRound,
            content = uiState.value.content,
            memo = uiState.value.memo,
            expenditureRequestDTOList = uiState.value.expenditureList
                .filter { it.money.isNotEmpty() }
                .map {
                    ExpenditureRequestItem(
                        name = it.nickname,
                        color = it.color,
                        amount = it.money.replace(",", "").toInt()
                    )
                }
        )
    }

    private fun getAccountDetail(id : Long){
        updateState(uiState.value.copy(id = id))

        viewModelScope.launch {
            accountRepository.getAccountDetail(id).collect {
                resultResponse(it, ::handleSuccessGetAccountDetail )
            }
        }
    }

    private fun handleSuccessGetAccountDetail(result : AccountDetailResponseVo){
        updateState(
            uiState.value.copy(
                date = result.date,
                nowRound = result.count,
                content = result.content,
                memo = result.memo,
                originExpenditureList = result.subsidyAvailable.map {
                    AccountExpenditureItemVo(
                        color = it.color,
                        nickname = it.nickname,
                        money = UnitFormatter.getMoneyFormat(it.amount.toString())
                    )
                }
            )
        )
        getSubsidyByCount(result.count)
    }

    private fun setOriginExpenditure() {
        val newList = uiState.value.expenditureList.map { item ->
            val matchingItem =  uiState.value.originExpenditureList.find { it.nickname == item.nickname }
            matchingItem ?: item
        }

        updateState(uiState.value.copy(expenditureList = newList))
    }
}