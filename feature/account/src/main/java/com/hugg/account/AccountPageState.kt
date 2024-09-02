package com.hugg.account

import com.hugg.domain.model.enums.AccountTabType
import com.hugg.feature.base.PageState
import com.hugg.feature.theme.ACCOUNT_UNIT_ALL

data class AccountPageState(
    val tabType : AccountTabType = AccountTabType.ALL,
    val filterText : String = ACCOUNT_UNIT_ALL
) : PageState