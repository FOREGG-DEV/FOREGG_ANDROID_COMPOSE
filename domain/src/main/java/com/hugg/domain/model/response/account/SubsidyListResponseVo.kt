package com.hugg.domain.model.response.account

import com.hugg.domain.model.enums.SubsidyColorType

data class SubsidyListResponseVo(
    val id : Long = -1,
    val nickname : String = "",
    val color : SubsidyColorType = SubsidyColorType.BLUE,
    val content : String = "",
    val amount : Int = -1,
    val expenditure : Int = -1,
    val available : Int = -1,
    val percent : Int = -1
)
