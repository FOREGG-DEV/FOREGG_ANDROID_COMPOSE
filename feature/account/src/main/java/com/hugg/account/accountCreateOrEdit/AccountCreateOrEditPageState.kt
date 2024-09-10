package com.hugg.account.accountCreateOrEdit

import com.hugg.domain.model.enums.CreateOrEditType
import com.hugg.domain.model.vo.account.AccountExpenditureItemVo
import com.hugg.feature.base.PageState
import com.hugg.feature.util.TimeFormatter
import com.hugg.feature.util.UserInfo

data class AccountCreateOrEditPageState(
    val pageType : CreateOrEditType = CreateOrEditType.CREATE,
    val date : String = TimeFormatter.getToday(),
    val nowRound : Int = UserInfo.info.round,
    val content : String = "",
    val expenditureList : List<AccountExpenditureItemVo> = emptyList(),
    val memo : String = "",
) : PageState {
    val isActiveBtn : Boolean = content.isNotEmpty() && expenditureList.any { it.money.isNotEmpty() }
}