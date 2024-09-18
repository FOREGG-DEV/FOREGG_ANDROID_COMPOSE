package com.hugg.domain.model.response.account

data class AccountResponseVo(
    val personalSum : Int = 0,
    val subsidySum : Int = 0,
    val subsidyAvailable : List<AccountSubsidyAvailableItemVo> = emptyList(),
    val total : String = "",
    val ledgerDetailResponseDTOS : List<AccountItemResponseVo> = emptyList()
)
