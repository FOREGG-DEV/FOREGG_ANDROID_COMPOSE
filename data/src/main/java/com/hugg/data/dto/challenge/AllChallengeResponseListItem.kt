package com.hugg.data.dto.challenge

import com.google.gson.annotations.SerializedName

data class AllChallengeResponseListItem(
    @SerializedName("id")
    val id: Long = 0,
    @SerializedName("point")
    val point: Int = 0,
    @SerializedName("image")
    val image: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("participants")
    val participants: Int = 0,
    @SerializedName("open")
    val open: Boolean = true,
    @SerializedName("participating")
    val participating: Boolean = true
)
