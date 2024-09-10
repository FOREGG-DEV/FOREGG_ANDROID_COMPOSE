package com.hugg.domain.model.request.account

import com.google.gson.annotations.SerializedName
import com.hugg.domain.model.enums.AccountColorType

data class AccountCreateRequestVo(
    @SerializedName("date")
    val date : String = "",
    @SerializedName("count")
    val count : Int = 0,
    @SerializedName("content")
    val content : String = "",
    @SerializedName("memo")
    val memo : String = "",
    @SerializedName("expenditureRequestDTOList")
    val expenditureRequestDTOList : List<ExpenditureRequestItem> = emptyList()
)

data class ExpenditureRequestItem(
    @SerializedName("name")
    val name : String = "",
    @SerializedName("color")
    val color : AccountColorType = AccountColorType.RED,
    @SerializedName("amount")
    val amount : Int = 0,
)
