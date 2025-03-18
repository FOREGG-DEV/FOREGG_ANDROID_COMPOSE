package com.hugg.domain.model.response.profile

import com.hugg.domain.model.enums.GenderType
import com.hugg.domain.model.enums.SurgeryType

data class ProfileDetailResponseVo(
    val id: Long = -1,
    val nickName : String = "",
    val surgeryType: SurgeryType = SurgeryType.THINK_SURGERY,
    val round : Int = 0,
    val startDate : String = "",
    val spouse : String = "",
    val genderType: GenderType = GenderType.FEMALE,
    val ssn : String = "",
    val shareCode : String = "",
    val challengeNickname : String = "",
)
