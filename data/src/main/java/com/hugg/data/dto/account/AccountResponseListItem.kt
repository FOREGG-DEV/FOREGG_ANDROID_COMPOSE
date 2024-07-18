package com.hugg.data.dto.account

import com.google.gson.annotations.SerializedName
import com.hugg.domain.model.enums.AccountType

data class AccountResponseListItem(
    @SerializedName("id")
    val id : Long = -1,
    @SerializedName("ledgerType")
    val ledgerType : AccountType = AccountType.PERSONAL_EXPENSE,
    @SerializedName("date")
    val date : String = "",
    @SerializedName("content")
    val content : String = "",
    @SerializedName("amount")
    val amount : Int = 0,
    @SerializedName("count")
    val count : Int = 0,
    @SerializedName("memo")
    val memo : String = "",
)
