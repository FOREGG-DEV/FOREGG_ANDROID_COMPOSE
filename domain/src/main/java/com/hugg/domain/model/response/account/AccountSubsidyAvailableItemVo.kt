package com.hugg.domain.model.response.account

import com.hugg.domain.model.enums.AccountColorType

data class AccountSubsidyAvailableItemVo(
    val color : AccountColorType = AccountColorType.RED,
    val nickname : String = "",
    val amount : Int = 0,
)
