package com.hugg.notification

import com.hugg.domain.model.enums.RecordType
import com.hugg.feature.base.PageState

data class InjMedDetailPageState(
    val date : String = "",
    val time : String = "",
    val name : String = "",
    val image : String = "",
    val explain : String = "",
    val pageType : RecordType = RecordType.ETC
) : PageState