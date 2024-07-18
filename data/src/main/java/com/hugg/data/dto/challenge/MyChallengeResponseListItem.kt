package com.hugg.data.dto.challenge

import com.google.gson.annotations.SerializedName

data class MyChallengeResponseListItem(
    @SerializedName("description")
    val description: String = "",
    @SerializedName("id")
    val id: Long = -1,
    @SerializedName("image")
    val image: String? = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("participants")
    val participants: Int = 0,
    @SerializedName("successDays")
    val successDays: List<String> = emptyList(),
    @SerializedName("weekOfMonth")
    val weekOfMonth: String = "",
    @SerializedName("lastSaturday")
    val lastSaturday : Boolean = false
)