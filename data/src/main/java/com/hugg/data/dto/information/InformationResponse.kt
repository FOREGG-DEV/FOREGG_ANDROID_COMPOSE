package com.hugg.data.dto.information

import com.google.gson.annotations.SerializedName
import com.hugg.domain.model.enums.InformationType

data class InformationResponse(
    @SerializedName("id")
    val id : Long = -1,
    @SerializedName("informationType")
    val informationType : InformationType = InformationType.NOTHING,
    @SerializedName("tag")
    val tag : List<String> = emptyList(),
    @SerializedName("image")
    val image : String = "",
    @SerializedName("url")
    val url : String = "",
)
