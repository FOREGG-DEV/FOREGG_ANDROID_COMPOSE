package com.hugg.data.dto.challenge

import com.google.gson.annotations.SerializedName

data class ChallengeSupportResponseItem(
    @SerializedName("userId")
    val id: Long = -1,
    @SerializedName("nickname")
    val nickname: String = "",
    @SerializedName("thoughts")
    val thoughts: String? = "",
    @SerializedName("supported")
    val supported: Boolean = false
)
