package com.hugg.domain.model.vo.account

import com.hugg.domain.model.enums.AccountType

data class AccountCardVo(
    val id : Long = -1,
    val date : String = "",
    val round : Int = -1,
    val type : AccountType = AccountType.PERSONAL_EXPENSE,
    val title : String = "",
    val money : Int = -1,
    val isSelected : Boolean = false
)
