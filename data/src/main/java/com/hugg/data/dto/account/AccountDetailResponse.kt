package com.hugg.data.dto.account

import com.google.gson.annotations.SerializedName
import com.hugg.domain.model.enums.AccountColorType

data class AccountDetailResponse(
    @SerializedName("date")
    val date : String = "",
    @SerializedName("count")
    val count : Int = 0,
    @SerializedName("content")
    val content : String = "",
    @SerializedName("memo")
    val memo : String = "",
    @SerializedName("expenditureRequestDTOList")
    val expenditureRequestDTOList : List<ExpenditureItem> = emptyList()
)

data class ExpenditureItem(
    @SerializedName("color")
    val color : AccountColorType = AccountColorType.RED,
    @SerializedName("name")
    val name : String = "",
    @SerializedName("amount")
    val amount : Int = 0,
)
