package com.hugg.data.dto.challenge

import com.google.gson.annotations.SerializedName

data class ChallengeSupportResponseItem(
    @SerializedName("id")
    val id: Long = -1,
    @SerializedName("nickname")
    val nickname: String = "",
    @SerializedName("thought")
    val thought: String = "",
    @SerializedName("supported")
    val supported: Boolean = false
)
