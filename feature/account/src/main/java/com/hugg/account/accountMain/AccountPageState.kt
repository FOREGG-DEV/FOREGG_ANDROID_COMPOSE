package com.hugg.account.accountMain

import com.hugg.domain.model.enums.AccountBottomSheetType
import com.hugg.domain.model.enums.AccountTabType
import com.hugg.domain.model.response.account.AccountSubsidyAvailableItemVo
import com.hugg.domain.model.vo.account.AccountCardVo
import com.hugg.feature.base.PageState
import com.hugg.feature.theme.ACCOUNT_ALL
import com.hugg.feature.util.UserInfo

data class AccountPageState(
    val tabType : AccountTabType = AccountTabType.ALL,
    val startDay : String = "",
    val endDay : String = "",
    val selectedYearMonth : String = "",
    val nowRound : Int = UserInfo.info.round,
    val personalExpense : String = "",
    val subsidyExpense : String = "",
    val subsidyList : List<AccountSubsidyAvailableItemVo> = emptyList(),
    val totalExpense : String = "",
    val filterList : List<String> = emptyList(),
    val selectedFilterList : List<String> = listOf(ACCOUNT_ALL),
    val accountList : List<AccountCardVo> = emptyList(),
    val isShowBottomSheet : Boolean = false,
    val isShowCreateRoundDialog : Boolean = false,
    val isShowDeleteDialog : Boolean = false,
    val selectedBottomSheetType: AccountBottomSheetType = AccountBottomSheetType.ONE_MONTH,
    val isDeleteMode : Boolean = false
) : PageState