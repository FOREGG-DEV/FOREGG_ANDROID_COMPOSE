package com.hugg.data.dto.account

import com.google.gson.annotations.SerializedName
import com.hugg.domain.model.enums.AccountColorType

data class SubsidyResponse(
    @SerializedName("subsidyDetailResponseDTOS")
    val subsidyDetailResponseDTOS : List<SubsidyResponseListItem> = emptyList()
)

data class SubsidyResponseListItem(
    @SerializedName("id")
    val id : Long = -1,
    @SerializedName("nickname")
    val nickname : String = "",
    @SerializedName("color")
    val color : AccountColorType = AccountColorType.BLUE,
    @SerializedName("content")
    val content : String = "",
    @SerializedName("amount")
    val amount : Int = -1,
    @SerializedName("expenditure")
    val expenditure : Int = -1,
    @SerializedName("available")
    val available : Int = -1,
    @SerializedName("percent")
    val percent : Int = -1
)