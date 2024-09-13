package com.hugg.data.dto.account

import com.google.gson.annotations.SerializedName

data class SubsidyDetailResponse(
    @SerializedName("nickname")
    val nickname : String = "",
    @SerializedName("content")
    val content : String = "",
    @SerializedName("count")
    val count : Int = 0,
    @SerializedName("amount")
    val amount : Int = 0
)