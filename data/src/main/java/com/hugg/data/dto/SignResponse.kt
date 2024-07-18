package com.hugg.data.dto

import com.google.gson.annotations.SerializedName

data class SignResponse(
    @SerializedName("keycode")
    val keycode : String? ="",
    @SerializedName("accessToken")
    val accessToken : String? ="",
    @SerializedName("refreshToken")
    val refreshToken : String? ="",
    @SerializedName("spouseCode")
    val spouseCode : String? ="",
)
