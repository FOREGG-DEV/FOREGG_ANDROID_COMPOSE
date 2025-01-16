package com.hugg.data.dto.challenge

import com.google.gson.annotations.SerializedName

data class ChallengeSupportResponse(
    @SerializedName("dto")
    val dto: List<ChallengeSupportResponseItem> = emptyList(),
    @SerializedName("currentPage")
    val currentPage: Int = 0,
    @SerializedName("totalPage")
    val totalPage: Int = 0,
    @SerializedName("totalItems")
    val totalItems: Int = 0
)
