package com.hugg.domain.model.vo.user

import com.hugg.domain.model.enums.GenderType

data class UserVo(
    val name : String = "",
    val ssn : String = "",
    val genderType: GenderType = GenderType.FEMALE,
    val spouse : String = "",
    val round : Int = -1,
)
