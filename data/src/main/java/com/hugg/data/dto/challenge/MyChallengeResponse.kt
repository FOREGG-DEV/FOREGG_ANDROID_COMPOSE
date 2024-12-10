package com.hugg.data.dto.challenge

import com.google.gson.annotations.SerializedName
import com.hugg.domain.model.response.challenge.MyChallengeListItemVo

data class MyChallengeResponse(
    @SerializedName("firstDateOfWeek")
    val firstDateOfWeek: String = "",
    @SerializedName("dtos")
    val dtos: List<MyChallengeListItemVo> = emptyList()
)
