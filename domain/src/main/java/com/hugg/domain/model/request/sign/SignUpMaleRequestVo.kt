package com.hugg.domain.model.request.sign

import com.google.gson.annotations.SerializedName

data class SignUpMaleRequestVo(
    @SerializedName("spouseCode")
    val spouseCode : String,
    @SerializedName("ssn")
    val ssn : String,
    @SerializedName("fcmToken")
    val fcmToken : String
)