package com.hugg.account

import com.hugg.domain.model.enums.AccountBottomSheetType
import com.hugg.domain.model.enums.AccountTabType
import com.hugg.domain.model.vo.account.AccountCardVo
import com.hugg.feature.base.PageState
import com.hugg.feature.theme.ACCOUNT_ALL

data class AccountPageState(
    val tabType : AccountTabType = AccountTabType.ALL,
    val filterText : String = ACCOUNT_ALL,
    val startDay : String = "",
    val endDay : String = "",
    val selectedYearMonth : String = "",
    val accountList : List<AccountCardVo> = emptyList(),
    val isShowBottomSheet : Boolean = false,
    val selectedBottomSheetType: AccountBottomSheetType = AccountBottomSheetType.ONE_MONTH,
    val totalExpense : String = "",
    val personalExpense : String = "",
) : PageState