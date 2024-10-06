package com.hugg.domain.model.vo.account

import com.hugg.domain.model.enums.AccountColorType

data class AccountCardVo(
    val ledgerId : Long = -1,
    val expenditureId : Long = -1,
    val date : String = "",
    val round : Int = -1,
    val color : AccountColorType = AccountColorType.RED,
    val cardName : String = "",
    val title : String = "",
    val money : Int = -1,
    val isSelected : Boolean = false
)
