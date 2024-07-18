package com.hugg.data.dto

import com.google.gson.annotations.SerializedName

data class ForeggJwtResponse(
    @SerializedName("accessToken")
    val accessToken : String = "",

    @SerializedName("refreshToken")
    val refreshToken : String = ""
)