package com.hugg.data.dto.account

import com.google.gson.annotations.SerializedName

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
    val expenditureRequestDTOList : List<SubsidyAvailableItem> = emptyList()
)
