package com.hugg.domain.model.response.account

import com.hugg.domain.model.enums.AccountColorType


data class AccountItemResponseVo(
    val ledgerId : Long = -1,
    val date : String = "",
    val round : Int = 0,
    val color : AccountColorType = AccountColorType.RED,
    val name : String = "",
    val content : String = "",
    val amount : Int = 0,
)
