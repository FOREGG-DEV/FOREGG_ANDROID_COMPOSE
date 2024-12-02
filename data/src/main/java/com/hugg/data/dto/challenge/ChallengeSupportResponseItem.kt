package com.hugg.data.dto.challenge

import com.google.gson.annotations.SerializedName

data class ChallengeSupportResponseItem(
    @SerializedName("id")
    val id: Long = -1,
    @SerializedName("challengeNickname")
    val challengeNickname: String = "",
    @SerializedName("thought")
    val thought: String = "",
    @SerializedName("success")
    val success: Boolean = false,
    @SerializedName("clap")
    val clap: Boolean = false,
    @SerializedName("support")
    val support: Boolean = false
)
