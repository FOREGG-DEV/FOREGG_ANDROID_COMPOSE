package com.hugg.data.dto.challenge

import com.google.gson.annotations.SerializedName

data class ChallengeListResponse(
    @SerializedName("dtos")
    val dtos: List<ChallengeResponseListItem> = emptyList()
)
