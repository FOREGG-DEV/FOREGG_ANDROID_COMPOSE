package com.hugg.domain.model.request.profile

import com.google.gson.annotations.SerializedName
import com.hugg.domain.model.enums.SurgeryType

data class EditMyInfoRequestVo(
    @SerializedName("surgeryType")
    val surgeryType : SurgeryType,
    @SerializedName("count")
    val count : Int?,
    @SerializedName("startDate")
    val startDate : String?,
)
