package com.hugg.domain.model.request.sign

import com.google.gson.annotations.SerializedName
import com.hugg.domain.model.enums.SurgeryType

data class SignUpRequestVo(
    @SerializedName("surgeryType")
    val surgeryType : SurgeryType,
    @SerializedName("count")
    val count : Int?,
    @SerializedName("startAt")
    val startAt : String?,
    @SerializedName("spouseCode")
    val spouseCode : String,
    @SerializedName("ssn")
    val ssn : String,
    @SerializedName("fcmToken")
    val fcmToken : String
)