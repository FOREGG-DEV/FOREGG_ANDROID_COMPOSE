package com.hugg.data.dto.account

import com.google.gson.annotations.SerializedName
import com.hugg.domain.model.enums.AccountColorType

data class AccountResponse(
    @SerializedName("personalSum")
    val personalSum : Int = 0,
    @SerializedName("subsidySum")
    val subsidySum : Int? = 0,
    @SerializedName("subsidyAvailable")
    val subsidyAvailable : List<SubsidyAvailableItem>? = emptyList(),
    @SerializedName("total")
    val total : String = "",
    @SerializedName("ledgerDetailResponseDTOS")
    val ledgerDetailResponseDTOS : List<AccountResponseListItem> = emptyList()
)

data class SubsidyAvailableItem(
    @SerializedName("color")
    val color : AccountColorType = AccountColorType.RED,
    @SerializedName("nickname")
    val nickname : String = "",
    @SerializedName("amount")
    val amount : Int = 0,
)
