package com.hugg.calendar.scheduleCreateOrEdit

import com.hugg.domain.model.enums.CreateOrEditType
import com.hugg.domain.model.enums.RecordType
import com.hugg.feature.base.PageState

data class ScheduleCreateOrEditPageState(
    val pageType : CreateOrEditType = CreateOrEditType.CREATE,
    val recordType: RecordType = RecordType.ETC,
    val id : Long = -1,
    val name : String = "",
    val isExpandDropDown : Boolean = false,
) : PageState