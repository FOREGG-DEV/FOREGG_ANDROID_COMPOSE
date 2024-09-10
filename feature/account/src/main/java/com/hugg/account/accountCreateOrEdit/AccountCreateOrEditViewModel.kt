package com.hugg.account.accountCreateOrEdit

import androidx.lifecycle.viewModelScope
import com.hugg.domain.model.enums.AccountColorType
import com.hugg.domain.model.enums.CreateOrEditType
import com.hugg.domain.model.request.account.AccountCreateRequestVo
import com.hugg.domain.model.request.account.ExpenditureRequestItem
import com.hugg.domain.model.response.account.SubsidyListResponseVo
import com.hugg.domain.model.vo.account.AccountExpenditureItemVo
import com.hugg.domain.repository.AccountRepository
import com.hugg.feature.base.BaseViewModel
import com.hugg.feature.theme.ACCOUNT_PERSONAL
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
        when(type){
            CreateOrEditType.CREATE -> getSubsidyByCount()
            CreateOrEditType.EDIT -> {}
        }
    }

    fun selectStartDate(date : String){
        updateState(uiState.value.copy(
            date = date
        ))
    }

    fun onClickPlusBtn(){
        if(uiState.value.nowRound == UserInfo.info.round) return
        updateState(uiState.value.copy(
            nowRound = uiState.value.nowRound + 1
        ))
        getSubsidyByCount()
    }

    fun onClickMinusBtn(){
        if(uiState.value.nowRound == 0) return
        updateState(uiState.value.copy(
            nowRound = uiState.value.nowRound - 1
        ))
        getSubsidyByCount()
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
            CreateOrEditType.EDIT -> {}
        }
    }

    private fun getSubsidyByCount(){
        viewModelScope.launch {
            accountRepository.getSubsidies(uiState.value.nowRound).collect {
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

    }

    private fun createAccount(){
        val request = getAccountCreateRequest()
        viewModelScope.launch {
            accountRepository.createAccount(request).collect {
                resultResponse(it, { emitEventFlow(AccountCreateOrEditEvent.SuccessCreateAccountEvent) } )
            }
        }
    }

    private fun getAccountCreateRequest() : AccountCreateRequestVo {
        return AccountCreateRequestVo(
            date = uiState.value.date,
            count = uiState.value.nowRound,
            content = uiState.value.content,
            memo = uiState.value.memo,
            expenditureRequestDTOList = uiState.value.expenditureList.map {
                ExpenditureRequestItem(
                    name = it.nickname,
                    color = it.color,
                    amount = it.money.replace(",", "").toInt()
                )
            }
        )
    }
}