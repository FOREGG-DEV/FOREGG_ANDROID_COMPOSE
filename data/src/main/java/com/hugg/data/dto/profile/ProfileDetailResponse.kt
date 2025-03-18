package com.hugg.data.dto.profile

import com.google.gson.annotations.SerializedName
import com.hugg.domain.model.enums.SurgeryType

data class ProfileDetailResponse(
    @SerializedName("id")
    val id: Long = -1,
    @SerializedName("nickname")
    val nickname : String = "",
    @SerializedName("surgeryType")
    val surgeryType : SurgeryType = SurgeryType.THINK_SURGERY,
    @SerializedName("count")
    val count : Int? = 0,
    @SerializedName("startDate")
    val startDate : String? = "",
    @SerializedName("spouse")
    val spouse : String? = "",
    @SerializedName("ssn")
    val ssn : String = "",
    @SerializedName("spouseCode")
    val spouseCode : String = "",
    @SerializedName("challengeNickname")
    val challengeNickname : String? = "",
)
