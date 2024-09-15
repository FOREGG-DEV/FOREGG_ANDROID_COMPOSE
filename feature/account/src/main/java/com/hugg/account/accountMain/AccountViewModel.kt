package com.hugg.account.accountMain

import androidx.lifecycle.viewModelScope
import com.hugg.domain.model.enums.AccountBottomSheetType
import com.hugg.domain.model.enums.AccountColorType
import com.hugg.domain.model.enums.AccountTabType
import com.hugg.domain.model.request.account.AccountGetConditionRequestVo
import com.hugg.domain.model.response.account.AccountItemResponseVo
import com.hugg.domain.model.response.account.AccountResponseVo
import com.hugg.domain.model.response.profile.ProfileDetailResponseVo
import com.hugg.domain.model.vo.account.AccountCardVo
import com.hugg.domain.repository.AccountRepository
import com.hugg.domain.repository.ProfileRepository
import com.hugg.feature.base.BaseViewModel
import com.hugg.feature.theme.ACCOUNT_ALL
import com.hugg.feature.theme.ACCOUNT_PERSONAL
import com.hugg.feature.theme.ACCOUNT_SUBSIDY
import com.hugg.feature.util.ForeggLog
import com.hugg.feature.util.TimeFormatter
import com.hugg.feature.util.UnitFormatter.getMoneyFormatWithUnit
import com.hugg.feature.util.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val profileRepository: ProfileRepository
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
    private var initialInit = false

    init {
        initDay(TimeFormatter.getPreviousMonthDate(), today)
        updateSelectedYearMonth()
    }

    fun initDay(start : String, end : String){
        if(uiState.value.startDay != start) updateStartDay(start)
        if(uiState.value.endDay != end) updateEndDay(end)
        initialInit = true
        setView()
    }

    fun setView(){
        if(!initialInit) return
        when(uiState.value.tabType){
            AccountTabType.ALL -> getAccountByCondition()
            AccountTabType.ROUND -> getAccountByRound()
            AccountTabType.MONTH -> getAccountByMonth()
        }
    }

    fun onClickTabType(type : AccountTabType){
        updateState(
            uiState.value.copy(
                tabType = type,
                selectedFilterList = listOf(ACCOUNT_ALL)
            )
        )
        setView()
    }

    fun onClickFilterBox(filterText : String){
        val newList = uiState.value.selectedFilterList.toMutableList()

        if (newList.contains(filterText)) newList.remove(filterText)
        else {
            if(filterText == ACCOUNT_ALL) newList.clear()
            if(filterText != ACCOUNT_ALL && newList.contains(ACCOUNT_ALL)) newList.remove(ACCOUNT_ALL)
            newList.add(filterText)
        }

        updateState(
            uiState.value.copy(selectedFilterList = newList)
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

    private fun getAccountByRound(){
        viewModelScope.launch {
            accountRepository.getByCount(uiState.value.nowRound).collect {
                resultResponse(it, ::handleGetSuccessAccount)
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
        updateSelectedRound(uiState.value.nowRound + 1)
    }

    fun onClickPrevRound(){
        if(uiState.value.nowRound == 0) return
        updateSelectedRound(uiState.value.nowRound - 1)
    }

    fun updateSelectedBottomSheetType(type : AccountBottomSheetType){
        updateState(
            uiState.value.copy(selectedBottomSheetType = type)
        )
    }

    fun onClickCreateRoundBtn(){
        viewModelScope.launch {
            accountRepository.createRound().collect {
                resultResponse(it, ::handleSuccessCreateRound)
            }
        }
    }

    fun showCreateRoundDialog(){
        updateState(
            uiState.value.copy(isShowCreateRoundDialog = true)
        )
    }

    fun cancelCreateRoundDialog(){
        updateState(
            uiState.value.copy(isShowCreateRoundDialog = false)
        )
    }

    fun showDeleteDialog(){
        updateState(
            uiState.value.copy(isShowDeleteDialog = true)
        )
    }

    fun cancelDeleteDialog(){
        updateState(
            uiState.value.copy(isShowDeleteDialog = false)
        )
    }

    fun onLongClickItem(id : Long){
        if(uiState.value.isDeleteMode) return
        else {
            val newList = uiState.value.accountList.map {
                it.copy(
                    isSelected = if (it.id == id) !it.isSelected else it.isSelected
                )
            }
            updateState(
                uiState.value.copy(
                    isDeleteMode = true,
                    accountList = newList
                )
            )
        }
    }

    fun onClickCard(id : Long){
        val newList = uiState.value.accountList.map {
            it.copy(
                isSelected = if (it.id == id) !it.isSelected else it.isSelected
            )
        }
        updateState(
            uiState.value.copy(
                isDeleteMode = newList.any { it.isSelected },
                accountList = newList
            )
        )
    }

    fun deleteAccount(){
        val deleteList = uiState.value.accountList.filter { it.isSelected }
        deleteList.forEachIndexed { index, accountCardVo ->
            viewModelScope.launch {
                accountRepository.delete(accountCardVo.id).collect {
                    resultResponse(it, { handleSuccessDeleteAccount(deleteList.size - 1 == index) })
                }
            }
        }
    }

    private fun handleGetSuccessAccount(result : AccountResponseVo){
        val filterList = when {
            uiState.value.selectedFilterList.contains(ACCOUNT_ALL) -> result.ledgerDetailResponseDTOS
            uiState.value.tabType == AccountTabType.ROUND -> result.ledgerDetailResponseDTOS.filter { it.name in uiState.value.selectedFilterList }
            uiState.value.selectedFilterList.contains(ACCOUNT_PERSONAL) -> result.ledgerDetailResponseDTOS.filter { it.color == AccountColorType.RED }
            else -> result.ledgerDetailResponseDTOS.filter { it.color != AccountColorType.RED }
        }

        updateState(
            uiState.value.copy(
                personalExpense = getMoneyFormatWithUnit(result.personalSum),
                subsidyExpense = getMoneyFormatWithUnit(result.subsidySum),
                totalExpense = getMoneyFormatWithUnit(result.total.toInt()),
                subsidyList = result.subsidyAvailable,
                accountList = getAccountCardList(filterList),
            )
        )
        updateFilterBoxList(result.ledgerDetailResponseDTOS)
    }

    private fun handleSuccessCreateRound(result : Unit){
        viewModelScope.launch {
            profileRepository.getMyInfo().collect{
                resultResponse(it, ::handleSuccessGetMyInfo)
            }
        }
    }

    private fun handleSuccessGetMyInfo(result : ProfileDetailResponseVo){
        UserInfo.updateInfo(result)
        updateSelectedRound(uiState.value.nowRound + 1)
    }

    private fun handleSuccessDeleteAccount(isComplete : Boolean){
        if(isComplete) {
            emitEventFlow(AccountEvent.SuccessDeleteAccountEvent)
            setView()
        }
    }

    private fun updateStartDay(start: String){
        updateState(
            uiState.value.copy(startDay = start)
        )
    }

    private fun updateEndDay(end : String){
        updateState(
            uiState.value.copy(endDay = end)
        )
    }

    private fun updateSelectedYearMonth(isChange : Boolean = false){
        updateState(
            uiState.value.copy(
                selectedYearMonth = TimeFormatter.getTodayYearAndMonthKor(year, month),
                isCurrentMonth = year == TimeFormatter.getYear(today) && month == TimeFormatter.getMonth(today)
            )
        )
        if(isChange) setView()
    }

    private fun updateSelectedRound(round : Int){
        updateState(
            uiState.value.copy(nowRound = round)
        )
        getAccountByRound()
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

    private fun getAccountCardList(list : List<AccountItemResponseVo>) : List<AccountCardVo>{
        return list.map {
            AccountCardVo(
                id = it.ledgerId,
                date = it.date,
                round = it.round,
                color = it.color,
                cardName = it.name,
                title = it.content,
                money = it.amount,
            )
        }
    }

    private fun updateFilterBoxList(list : List<AccountItemResponseVo>){
        val filterBoxList = mutableListOf<String>()

        if(list.isNotEmpty()) filterBoxList.add(ACCOUNT_ALL)
        if(list.any { it.color == AccountColorType.RED }) filterBoxList.add(ACCOUNT_PERSONAL)
        if((uiState.value.tabType == AccountTabType.ALL || uiState.value.tabType == AccountTabType.MONTH) && list.any { it.color != AccountColorType.RED }) filterBoxList.add(ACCOUNT_SUBSIDY)
        if(uiState.value.tabType == AccountTabType.ROUND) filterBoxList.addAll(list.filter { it.name != ACCOUNT_PERSONAL }.map { it.name }.distinct())

        updateState(
            uiState.value.copy(filterList = filterBoxList)
        )
    }
}