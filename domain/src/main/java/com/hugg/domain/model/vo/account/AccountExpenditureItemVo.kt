package com.hugg.domain.model.vo.account

import com.hugg.domain.model.enums.AccountColorType

data class AccountExpenditureItemVo(
    val color : AccountColorType = AccountColorType.RED,
    val nickname : String = "",
    val money : String = "",
)
