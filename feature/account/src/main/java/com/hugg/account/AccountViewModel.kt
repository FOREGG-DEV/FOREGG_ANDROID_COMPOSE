package com.hugg.account

import androidx.lifecycle.viewModelScope
import com.hugg.domain.model.enums.AccountBottomSheetType
import com.hugg.domain.model.enums.AccountTabType
import com.hugg.domain.model.enums.AccountType
import com.hugg.domain.model.request.account.AccountGetConditionRequestVo
import com.hugg.domain.model.response.account.AccountResponseVo
import com.hugg.domain.repository.AccountRepository
import com.hugg.feature.base.BaseViewModel
import com.hugg.feature.theme.ACCOUNT_ALL
import com.hugg.feature.theme.ACCOUNT_PERSONAL
import com.hugg.feature.theme.ACCOUNT_SUBSIDY
import com.hugg.feature.util.TimeFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : BaseViewModel<AccountPageState>(
    AccountPageState()
) {

    private val today = TimeFormatter.getToday()
    private var year = TimeFormatter.getYear(today)
    private var month =  TimeFormatter.getMonth(today)
    private var round by Delegates.notNull<Int>()

    init {
        initDay(TimeFormatter.getPreviousMonthDate(), today)
    }

    fun initDay(start : String, end : String){
        updateState(
            uiState.value.copy(
                startDay = start,
                endDay = end,
            )
        )
    }

    fun onClickTabType(type : AccountTabType){
        updateState(
            uiState.value.copy(tabType = type)
        )
    }

    fun onClickFilterBox(filterText : String){
        updateState(
            uiState.value.copy(filterText = filterText)
        )
        getAccountByCondition()
    }

    fun onClickBottomSheetOnOff(){
        updateState(
            uiState.value.copy(isShowBottomSheet = !uiState.value.isShowBottomSheet)
        )
    }

    fun getAccountByCondition(){
        val request = AccountGetConditionRequestVo(
            from = uiState.value.startDay,
            to = uiState.value.endDay
        )
        viewModelScope.launch {
            accountRepository.getByCondition(request).collect{
                resultResponse(it, ::handleGetSuccessAccount)
            }
        }
    }

    fun updateSelectedBottomSheetType(type : AccountBottomSheetType){
        updateState(
            uiState.value.copy(selectedBottomSheetType = type)
        )
    }

    private fun handleGetSuccessAccount(result : AccountResponseVo){
        val filterList = result.accountList.filter {
            when(uiState.value.filterText) {
                ACCOUNT_PERSONAL -> it.type == AccountType.PERSONAL_EXPENSE
                ACCOUNT_SUBSIDY -> it.type == AccountType.SUBSIDY
                else -> true
            }
        }
        updateState(
            uiState.value.copy(accountList = filterList)
        )
    }

    private fun getMoneyFormat(money : Int) : String {
        val koreanFormat = NumberFormat.getNumberInstance(Locale("ko"))
        return koreanFormat.format(money) + "원"
    }
}