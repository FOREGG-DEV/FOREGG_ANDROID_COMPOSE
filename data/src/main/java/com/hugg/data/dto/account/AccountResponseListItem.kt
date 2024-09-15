package com.hugg.data.dto.account

import com.google.gson.annotations.SerializedName
import com.hugg.domain.model.enums.AccountColorType

data class AccountResponseListItem(
    @SerializedName("ledgerId")
    val ledgerId : Long = -1,
    @SerializedName("date")
    val date : String = "",
    @SerializedName("count")
    val count : Int = 0,
    @SerializedName("color")
    val color : AccountColorType = AccountColorType.ALL,
    @SerializedName("name")
    val name : String = "",
    @SerializedName("content")
    val content : String = "",
    @SerializedName("amount")
    val amount : Int = 0,
)
