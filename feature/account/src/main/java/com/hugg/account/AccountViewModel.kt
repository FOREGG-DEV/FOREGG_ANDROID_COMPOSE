package com.hugg.account

import androidx.lifecycle.viewModelScope
import com.hugg.domain.model.enums.AccountBottomSheetType
import com.hugg.domain.model.enums.AccountTabType
import com.hugg.domain.model.enums.AccountType
import com.hugg.domain.model.request.account.AccountGetConditionRequestVo
import com.hugg.domain.model.response.account.AccountResponseVo
import com.hugg.domain.model.response.account.SubsidyListResponseVo
import com.hugg.domain.repository.AccountRepository
import com.hugg.feature.base.BaseViewModel
import com.hugg.feature.theme.ACCOUNT_ALL
import com.hugg.feature.theme.ACCOUNT_PERSONAL
import com.hugg.feature.theme.ACCOUNT_SUBSIDY
import com.hugg.feature.util.TimeFormatter
import com.hugg.feature.util.UnitFormatter.getMoneyFormat
import com.hugg.feature.util.UserInfo
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

    companion object{
        const val JANUARY = 1
        const val DECEMBER = 12
    }

    private val today = TimeFormatter.getToday()
    private var year = TimeFormatter.getYear(today)
    private var month = TimeFormatter.getMonth(today)
    private var round = UserInfo.info.round

    init {
        initDay(TimeFormatter.getPreviousMonthDate(), today)
        updateSelectedYearMonth()
    }

    fun initDay(start : String, end : String){
        if(uiState.value.startDay != start) updateStartDay(start)
        if(uiState.value.endDay != end) updateEndDay(end)
        setView()
    }

    private fun setView(){
        when(uiState.value.tabType){
            AccountTabType.ALL -> getAccountByCondition()
            AccountTabType.ROUND -> {
                getSubsidies()
            }
            AccountTabType.MONTH -> getAccountByMonth()
        }
    }

    fun onClickTabType(type : AccountTabType){
        updateState(
            uiState.value.copy(tabType = type)
        )
        setView()
    }

    fun onClickFilterBox(filterText : String){
        updateState(
            uiState.value.copy(filterText = filterText)
        )
        setView()
    }

    fun onClickBottomSheetOnOff(){
        updateState(
            uiState.value.copy(isShowBottomSheet = !uiState.value.isShowBottomSheet)
        )
    }

    private fun getAccountByCondition(){
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

    private fun getSubsidies(){
        viewModelScope.launch {
            accountRepository.getSubsidies(uiState.value.nowRound).collect {
                resultResponse(it, ::handleGetSuccessSubsidies)
            }
        }
    }

    fun onClickNextMonth(){
        if(month == DECEMBER){
            year++
            month = JANUARY
        }
        else{
            month++
        }
        updateSelectedYearMonth(true)
    }

    fun onClickPrevMonth(){
        if(month == JANUARY){
            year--
            month = DECEMBER
        }
        else{
            month--
        }
        updateSelectedYearMonth(true)
    }

    fun onClickNextRound(){
        round++
        updateSelectedRound()
    }

    fun onClickPrevRound(){
        if(round == 0) return
        round--
        updateSelectedRound()
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
            uiState.value.copy(
                personalExpense = getMoneyFormat(result.personalMoney),
                totalExpense = getMoneyFormat(result.allExpendMoney),
                accountList = filterList
            )
        )
    }

    private fun handleGetSuccessSubsidies(result : List<SubsidyListResponseVo>){
        updateState(
            uiState.value.copy(subsidyList = result)
        )
    }

    private fun updateStartDay(start: String){
        updateState(
            uiState.value.copy(startDay = start,)
        )
    }

    private fun updateEndDay(end : String){
        updateState(
            uiState.value.copy(endDay = end)
        )
    }

    private fun updateSelectedYearMonth(isChange : Boolean = false){
        updateState(
            uiState.value.copy(selectedYearMonth = TimeFormatter.getTodayYearAndMonthKor(year, month))
        )
        if(isChange) setView()
    }

    private fun updateSelectedRound(){
        updateState(
            uiState.value.copy(nowRound = round)
        )
        setView()
    }

    private fun getAccountByMonth(){
        val request = getByMonthRequest()
        viewModelScope.launch {
            accountRepository.getByMonth(request).collect{
                resultResponse(it, ::handleGetSuccessAccount)
            }
        }
    }

    private fun getByMonthRequest() : String{
        val requestMonth = String.format("%02d", month)
        return "$year-$requestMonth"
    }
}