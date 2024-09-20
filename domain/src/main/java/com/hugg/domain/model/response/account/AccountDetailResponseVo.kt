package com.hugg.domain.model.response.account

import com.google.gson.annotations.SerializedName

data class AccountDetailResponseVo(
    val date : String = "",
    val count : Int = 0,
    val content : String = "",
    val memo : String = "",
    val subsidyAvailable : List<AccountSubsidyAvailableItemVo> = emptyList(),
)
